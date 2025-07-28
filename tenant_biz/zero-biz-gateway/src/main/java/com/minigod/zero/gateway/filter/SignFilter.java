package com.minigod.zero.gateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minigod.zero.core.launch.constant.ESourceType;
import com.minigod.zero.core.launch.constant.TokenConstant;
import com.minigod.zero.gateway.provider.ResponseProvider;
import com.minigod.zero.gateway.utils.SignUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

import java.nio.charset.StandardCharsets;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.CACHED_REQUEST_BODY_ATTR;

/**
 * APP端请求签名验签拦截器
 */
@Slf4j
@Component
public class SignFilter implements GlobalFilter, Ordered {

	@Resource
	private ObjectMapper objectMapper;

	public static final String SIGNATURE = "Sign";
	public static final String TIMESTAMP = "TS";
	private static final String authUrl = "/oauth/authorize";

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpResponse resp = exchange.getResponse();
		HttpMethod method = exchange.getRequest().getMethod();
		String sourceType = exchange.getRequest().getHeaders().getFirst(TokenConstant.SOURCE_TYPE);
		String deviceCode = exchange.getRequest().getHeaders().getFirst(TokenConstant.DEVICE_CODE);
		String urlSign = exchange.getRequest().getHeaders().getFirst(SIGNATURE);
		String timestamp = exchange.getRequest().getHeaders().getFirst(TIMESTAMP);
		String path = exchange.getRequest().getPath().toString();
		if (path.endsWith("/v2/api-docs")) {
			return chain.filter(exchange); //API文档不验签
		}
		if (StringUtils.isBlank(sourceType) && !authUrl.equals(exchange.getRequest().getPath().toString())) {
			//return Mono.error(() -> new ValidationException("请求头Source-Type不能为空"));
			return unSign(resp, "请求头Source-Type不能为空");
		}
		if (sourceType.equals(ESourceType.H5.getName()) || method.equals(HttpMethod.GET)) {
			return chain.filter(exchange); //不验签
		}
		if (StringUtils.isBlank(deviceCode)) {
			return unSign(resp, "请求头设备号不能为空");
		}
		if (StringUtils.isBlank(urlSign) || StringUtils.isBlank(timestamp)) {
			//return Mono.error(() -> new ValidationException("请求头Sign或TS不能为空"));
			return unSign(resp, "请求头Sign或TS不能为空");
		}
		MediaType mediaType = exchange.getRequest().getHeaders().getContentType();
		if (HttpMethod.POST.equals(method) && null != mediaType
			&& (mediaType.isCompatibleWith(MediaType.APPLICATION_JSON) || mediaType.isCompatibleWith(MediaType.APPLICATION_FORM_URLENCODED))) {
			String requestBody = (String) exchange.getAttributes().get(CACHED_REQUEST_BODY_ATTR);
			if (!SignUtil.verifySign(requestBody, timestamp, urlSign)) {
				//return Mono.error(() -> new ValidationException("APP接口验签失败!!!"));
				return unSign(resp, "APP接口验签失败!!!");
			}
			return chain.filter(exchange);
		}
		if (method.equals(HttpMethod.GET) || method.equals(HttpMethod.DELETE)) {
			String params = exchange.getRequest().getURI().getQuery();
			if (!SignUtil.verifySign(params, timestamp, urlSign)) {
				//return Mono.error(() -> new ValidationException("APP接口验签失败!!!"));
				return unSign(resp, "APP接口验签失败!!!");
			}
		}
		return chain.filter(exchange);
	}


	@Override
	public int getOrder() {
		return -500;
	}

	private Mono<Void> unSign(ServerHttpResponse resp, String msg) {
		resp.setStatusCode(HttpStatus.OK);
		resp.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
		String result = "";
		try {
			result = objectMapper.writeValueAsString(ResponseProvider.unSign(msg));
		} catch (JsonProcessingException e) {
			log.error(e.getMessage(), e);
		}
		DataBuffer buffer = resp.bufferFactory().wrap(result.getBytes(StandardCharsets.UTF_8));
		return resp.writeWith(Flux.just(buffer));
	}

}
