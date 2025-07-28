package com.minigod.zero.gateway.filter;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.util.DateUtils;
import com.alibaba.nacos.common.utils.MD5Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minigod.zero.core.jwt.Jwt2Util;
import com.minigod.zero.core.jwt.props.JwtProperties;
import com.minigod.zero.core.launch.constant.TokenConstant;
import com.minigod.zero.gateway.props.AuthProperties;
import com.minigod.zero.gateway.provider.AuthProvider;
import com.minigod.zero.gateway.provider.RequestProvider;
import com.minigod.zero.gateway.provider.ResponseProvider;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
import java.util.*;

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
        ServerHttpResponse resp = exchange.getResponse();
        //校验 Token 放行
        String originalRequestUrl = RequestProvider.getOriginalRequestUrl(exchange);
        String path = exchange.getRequest().getURI().getPath();

        if (isSkip(path) || isSkip(originalRequestUrl)) {
            return chain.filter(exchange);
        }
        //校验 Token 合法性
        String headerToken = exchange.getRequest().getHeaders().getFirst(TokenConstant.TENANT_TOKEN);

        if (StringUtils.isBlank(headerToken)) {
            return unAuth(resp, "鉴权失败：缺失令牌");
        }
        String token = Jwt2Util.getToken(headerToken);
        Claims claims = Jwt2Util.parseJWT(token);
        if (token == null || claims == null) {
            return unAuth(resp, "鉴权失败：请求未授权");
        }
        //校验 登录Token 状态
        if (jwtProperties.getState()) {
            String custId = String.valueOf(claims.get(TokenConstant.CUST_ID));
			log.info("缓存key:{}",Jwt2Util.getAccessTokenKey(custId, token));
            String accessToken = Jwt2Util.getAccessToken(custId, token);
            if (StringUtils.isBlank(accessToken) || "null".equals(accessToken)) {
                log.warn("鉴权失败：令牌已失效：" + custId);
                return unAuth(resp, "鉴权失败：令牌已失效");
            } else if (!token.equalsIgnoreCase(accessToken)) {
				log.info("用戶token={}，緩存token={}",token,accessToken);
                Claims newClaims = Jwt2Util.parseJWT(accessToken);
                String loginTime = String.valueOf(newClaims.get(TokenConstant.LOGIN_TIME));
				if (StringUtils.isEmpty(loginTime) || loginTime.equals("null")){
					loginTime = DateUtils.format(new Date());
				}
                String terminal = String.valueOf(newClaims.get(TokenConstant.TERMINAL));
                Map<String, String> data = new HashMap<>();
                data.put("loginTime", loginTime);
                data.put("terminal", terminal);
                data.put("message", "您的账号在"+loginTime+"在其他设备上登录，请重新登录，如非本人操作，请尽快修改登录密码");
                log.warn("已在别的设备登录：" + custId);
                return expire(resp, JSONObject.toJSONString(data));
            }

            /**
             * APP端接口鉴权
             * 交易下单接口权限拦截需要区分是否是衍生品，需要把拦截从网关去掉，由交易服务鉴权，所以status是 0
             */
            Map<Object, Object> appFunc = Jwt2Util.getRedisTemplate().opsForHash().entries(TokenConstant.APP_FUNC_KEY);
            List<Object> needPath = new ArrayList<>(appFunc.values());
            if (needPath != null && needPath.stream().anyMatch(item -> (null != item) && ((String) item).contains(originalRequestUrl))) {
                String appAuth = String.valueOf(Jwt2Util.getRedisTemplate().opsForHash().get(TokenConstant.APP_AUTH_KEY, custId));
                if (StringUtils.isNotBlank(appAuth) && !"null".equals(appAuth)) {
                    String[] custAuth = appAuth.replace("[", "").replace("]", "").split(",");
                    boolean checkFlag = false;
                    for (String authId : custAuth) {
                        String authUrl = String.valueOf(Jwt2Util.getRedisTemplate().opsForHash().get(TokenConstant.APP_FUNC_KEY, authId));
                        if (StringUtils.isNotBlank(authUrl) && !"null".equals(authUrl) && authUrl.contains(originalRequestUrl)) {
                            checkFlag = true;
                            break;
                        }
                    }
                    if (checkFlag) {
                        log.error(originalRequestUrl + " 无操作权限：" + appAuth);
                        return unOper(resp, "请求失败：无操作权限");
                    }
                }
            }

            // 交易接口校验交易解锁Token
            if (isTrade(path) || isTrade(originalRequestUrl)) {
                String sessionId = MD5Utils.md5Hex(token, "UTF-8");
                if (!Jwt2Util.validateJwtToken(sessionId)) {
                    return unTrade(resp, "交易鉴权失败：令牌已失效");
                }
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

    private boolean isTrade(String path) {
        return AuthProvider.getTradeFilterUrl().stream().anyMatch(pattern -> antPathMatcher.match(pattern, path));
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

    private Mono<Void> unOper(ServerHttpResponse resp, String msg) {
        resp.setStatusCode(HttpStatus.OK);
        resp.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        String result = "";
        try {
            result = objectMapper.writeValueAsString(ResponseProvider.unOper(msg));
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
        DataBuffer buffer = resp.bufferFactory().wrap(result.getBytes(StandardCharsets.UTF_8));
        return resp.writeWith(Flux.just(buffer));
    }

    private Mono<Void> unTrade(ServerHttpResponse resp, String msg) {
        resp.setStatusCode(HttpStatus.OK);
        resp.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        String result = "";
        try {
            result = objectMapper.writeValueAsString(ResponseProvider.unTrade(msg));
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

    @Override
    public int getOrder() {
        return -100;
    }

}
