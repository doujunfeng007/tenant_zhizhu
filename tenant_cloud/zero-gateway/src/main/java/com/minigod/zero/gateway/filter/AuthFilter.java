package com.minigod.zero.gateway.filter;

import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minigod.zero.gateway.provider.ResponseProvider;
import com.minigod.zero.core.jwt.JwtUtil;
import com.minigod.zero.core.jwt.props.JwtProperties;
import com.minigod.zero.core.launch.constant.TokenConstant;
import com.minigod.zero.gateway.props.AuthProperties;
import com.minigod.zero.gateway.provider.AuthProvider;
import com.minigod.zero.gateway.provider.RequestProvider;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 鉴权认证
 *
 * @author Chill
 */
@Slf4j
@Component
@AllArgsConstructor
public class AuthFilter implements GlobalFilter, Ordered {
	private final AuthProperties authProperties;
	private final ObjectMapper objectMapper;
	private final JwtProperties jwtProperties;
	private final AntPathMatcher antPathMatcher = new AntPathMatcher();

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		// 后台Token放行
		String originalRequestUrl = RequestProvider.getOriginalRequestUrl(exchange);
		String path = exchange.getRequest().getURI().getPath();
		if (isSkip(path) || isSkip(originalRequestUrl)) {
			return chain.filter(exchange);
		}
		ServerHttpResponse resp = exchange.getResponse();
		// 解析行情客户端身份参数
		String quotToken = exchange.getRequest().getHeaders().getFirst(TokenConstant.QUOT_TOKEN);
		if (StringUtils.isNotBlank(quotToken)) {
			String token = JwtUtil.getToken(quotToken);
			Claims claims = JwtUtil.parseJWT(token);
			if (token != null && claims != null) {
				//判断 Token 状态
				if (jwtProperties.getState()) {
					String tenantId = String.valueOf(claims.get(TokenConstant.TENANT_ID));
					String custId = String.valueOf(claims.get(TokenConstant.CUST_ID));
					String accessToken = JwtUtil.getAccessToken(tenantId, custId, null, token);
					if (StringUtils.isBlank(accessToken) || "null".equals(accessToken)) {
						log.warn("行情鉴权失败：令牌已失效：" + custId);
						return unAuth(resp, "行情鉴权失败：令牌已失效"); // 无需加载兜底权限
					} else if (!token.equalsIgnoreCase(accessToken)) {
						Claims newClaims = JwtUtil.parseJWT(accessToken);
						String loginTime = String.valueOf(newClaims.get(TokenConstant.LOGIN_TIME));
						Map<String, String> data = new HashMap<>();
						data.put("loginTime", loginTime);
						data.put("message", "已在别的设备登录");
						log.warn("已在别的设备登录：" + custId);
						return expire(resp, JSONObject.toJSONString(data));
					} else {
						// 将参数写入Body
						String terminal = String.valueOf(claims.get(TokenConstant.TERMINAL));
						exchange = this.updateHeaders(exchange, tenantId, terminal, custId);
						return chain.filter(exchange);
					}
				}
			}
		}

		// 校验后台Token合法性
		String headerToken = exchange.getRequest().getHeaders().getFirst(TokenConstant.HEADER);

		if (StringUtils.isBlank(headerToken)) {
			return unAuth(resp, "鉴权失败：缺失令牌");
		}
		String token = JwtUtil.getToken(headerToken);

		Claims claims = JwtUtil.parseJWT(token);
		if (token == null || claims == null) {
			return unAuth(resp, "鉴权失败：请求未授权");
		}
		// 校验登录Token状态
		if (jwtProperties.getState()) {
			String tenantId = String.valueOf(claims.get(TokenConstant.TENANT_ID));
			String userId = String.valueOf(claims.get(TokenConstant.USER_ID));
			String account = String.valueOf(claims.get(TokenConstant.ACCOUNT));
			String accessToken = JwtUtil.getAccessToken(tenantId, userId, account, token);
			if (StringUtils.isBlank(accessToken) || "null".equals(accessToken)) {
				log.warn("鉴权失败：令牌已失效：" + userId);
				return unAuth(resp, "鉴权失败：令牌已失效");
			} else if (!token.equalsIgnoreCase(accessToken)) {
				Claims newClaims = JwtUtil.parseJWT(accessToken);
				String loginTime = String.valueOf(newClaims.get(TokenConstant.LOGIN_TIME));
				String terminal = String.valueOf(newClaims.get(TokenConstant.TERMINAL));
				Map<String, String> data = new HashMap<>();
				data.put("loginTime", loginTime);
				data.put("terminal", terminal);
				data.put("message", "已在别的设备登录");
				log.warn("已在别的设备登录：" + userId);
				return expire(resp, JSONObject.toJSONString(data));
			}
		}
		return chain.filter(exchange);
	}

	private boolean isSkip(String path) {
		return AuthProvider.getDefaultSkipUrl().stream().anyMatch(pattern -> antPathMatcher.match(pattern, path))
			|| authProperties.getSkipUrl().stream().anyMatch(pattern -> antPathMatcher.match(pattern, path))
 			|| authProperties.getAuth().stream().anyMatch(auth -> antPathMatcher.match(auth.getPattern(), path))
 			|| authProperties.getBasic().stream().anyMatch(basic -> antPathMatcher.match(basic.getPattern(), path))
			|| authProperties.getSign().stream().anyMatch(sign -> antPathMatcher.match(sign.getPattern(), path));
	}

	private Mono<Void> unAuth(ServerHttpResponse resp, String msg) {
		resp.setStatusCode(HttpStatus.OK);
		resp.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
		String result = "";
		try {
			result = objectMapper.writeValueAsString(ResponseProvider.unAuth(msg));
		} catch (JsonProcessingException e) {
			log.error(e.getMessage(), e);
		}
		DataBuffer buffer = resp.bufferFactory().wrap(result.getBytes(StandardCharsets.UTF_8));
		return resp.writeWith(Flux.just(buffer));
	}

	private Mono<Void> expire(ServerHttpResponse resp, String msg) {
		resp.setStatusCode(HttpStatus.OK);
		resp.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
		String result = "";
		try {
			result = objectMapper.writeValueAsString(ResponseProvider.expire(msg));
		} catch (JsonProcessingException e) {
			log.error(e.getMessage(), e);
		}
		DataBuffer buffer = resp.bufferFactory().wrap(result.getBytes(StandardCharsets.UTF_8));
		return resp.writeWith(Flux.just(buffer));
	}

	private ServerWebExchange updateHeaders(ServerWebExchange exchange, String tenantId, String terminal, String custId) {
		ServerHttpRequest request = exchange.getRequest();
		HttpHeaders headers = request.getHeaders();
		HttpHeaders newHeaders = new HttpHeaders();
		newHeaders.putAll(headers);
		newHeaders.add("tenantId", tenantId);
		newHeaders.add(TokenConstant.TERMINAL, terminal);
		newHeaders.add(TokenConstant.CUST_ID, custId);

		// Create new request with modified headers
		ServerHttpRequest modifiedRequest = new ServerHttpRequestDecorator(request) {
			@Override
			public HttpHeaders getHeaders() {
				return newHeaders;
			}
		};
		return exchange.mutate().request(modifiedRequest).build();
	}

	@Override
	public int getOrder() {
		return -100;
	}

}
