/*
 * Copyright (c)  2022. ZSDP Inc. All rights reserved.
 */
package com.minigod.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.common.utils.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minigod.common.constant.CommonConstant;
import com.minigod.zero.core.i18n.utils.I18nUtil;
import com.minigod.zero.core.jwt.Jwt2Util;
import com.minigod.zero.core.jwt.JwtUtil;
import com.minigod.zero.core.launch.constant.TokenConstant;
import com.minigod.gateway.props.AuthProperties;
import com.minigod.gateway.provider.AuthProvider;
import com.minigod.gateway.provider.RequestProvider;
import com.minigod.gateway.provider.ResponseProvider;
import com.minigod.zero.core.i18n.utils.I18nUtil;
import com.minigod.zero.core.jwt.Jwt2Util;
import com.minigod.zero.core.jwt.JwtUtil;
import com.minigod.zero.core.launch.constant.TokenConstant;
import com.minigod.zero.core.tool.utils.DateUtil;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 鉴权认证
 *
 * @author zsdp
 */
@Slf4j
@Component
@AllArgsConstructor
public class AuthFilter implements GlobalFilter, Ordered {
	private final ObjectMapper objectMapper;
	private final AuthProperties authProperties;
	private final AntPathMatcher antPathMatcher = new AntPathMatcher();

	private static final String WEB= "web";
	private static final String SOURCE_TYPE = "Source-Type";

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		//校验 Token 放行
		String originalRequestUrl = RequestProvider.getOriginalRequestUrl(exchange);
		String path = exchange.getRequest().getURI().getPath();
		if (isSkip(path) || isSkip(originalRequestUrl)) {
			return chain.filter(exchange);
		}
		//校验 Token 合法性
		ServerHttpResponse resp = exchange.getResponse();
		String token = this.getToken(exchange);
		if (StringUtils.isBlank(token) ) {
			return unAuth(resp, I18nUtil.getMessage(CommonConstant.PLEASE_LOGIN));
		}
		Claims claims = Jwt2Util.parseJWT(token);
		if (claims == null){
			return unAuth(resp, I18nUtil.getMessage(CommonConstant.ILLEGAL_REQUEST,null,exchange));
		}
		String tenantId = String.valueOf(claims.get(TokenConstant.TENANT_ID));
		String custId = String.valueOf(claims.get(TokenConstant.USER_ID));
		String clientId = String.valueOf(claims.get(TokenConstant.CLIENT_ID));
		//内部服务不校验token状态
		if (authProperties.getClients().contains(clientId)){
			return chain.filter(exchange);
		}
		String sourceType = exchange.getRequest().getHeaders().getFirst(SOURCE_TYPE);
		String accessToken = null;
		if (WEB.equals(sourceType)){
			accessToken = JwtUtil.getAccessToken(tenantId,custId,"",token);
		}else{
			accessToken = Jwt2Util.getAccessToken(custId, token);
		}
		if (StringUtils.isBlank(accessToken) || "null".equals(accessToken)) {
			return unAuth(resp, I18nUtil.getMessage(CommonConstant.LOGIN_AGAIN_PLEASE,null,exchange));
		}
		if (!token.equalsIgnoreCase(accessToken)) {
			Claims newClaims = Jwt2Util.parseJWT(accessToken);
			String loginTime = String.valueOf(newClaims.get(TokenConstant.LOGIN_TIME));
			String terminal = String.valueOf(newClaims.get(TokenConstant.TERMINAL));
			Map<String, String> data = new HashMap<>();
			if (StringUtils.isBlank(loginTime) || loginTime.equals("null")){
				loginTime =DateUtil.formatDateTime(new Date());
			}
			data.put("loginTime", loginTime);
			data.put("terminal", terminal);
			String message = I18nUtil.getMessage(CommonConstant.LOGGED_IN_ON_ANOTHER_DEVICE,null,exchange);
			if (!StringUtils.isEmpty(message)){
				message = message.replace("#time",loginTime);
			}
			data.put("message",message);
			return expire(resp, JSONObject.toJSONString(data));
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
		resp.setStatusCode(HttpStatus.UNAUTHORIZED);
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
		resp.setStatusCode(HttpStatus.UNAUTHORIZED);
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



	private String getToken(ServerWebExchange exchange) {
		String tokenStr = exchange.getRequest().getHeaders().getFirst(TokenConstant.TENANT_TOKEN);
		if (com.alibaba.cloud.commons.lang.StringUtils.isBlank(tokenStr)) {
			return null;
		}
		String token = tokenStr.split(" ")[1];
		if (com.alibaba.cloud.commons.lang.StringUtils.isBlank(token)) {
			return null;
		}
		return token;
	}

	@Override
	public int getOrder() {
		return -100;
	}

}
