package com.minigod.zero.gateway.filter;

import com.alibaba.druid.support.json.JSONUtils;
import com.minigod.zero.core.jwt.Jwt2Util;
import com.minigod.zero.core.launch.constant.TokenConstant;
import io.jsonwebtoken.Claims;
import io.netty.buffer.ByteBufAllocator;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author chen
 * @ClassName SaasGatewayFilterFactory.java
 * @Description 管理后台请求Saas端的接口
 * @createTime 2024年11月23日 16:28:00
 */
@Slf4j
@Component
public class SaasGatewayFilterFactory extends AbstractGatewayFilterFactory<SaasGatewayFilterFactory.Config> {

	@Value("${saas.customer.tenantId}")
	private String tenantId;

	public SaasGatewayFilterFactory() {
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest().mutate().build();
			log.info("拦截到SAAS请求->url:{}", request.getURI());
			Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
			String uri = route.getUri().toString();
			String newUri = uri + request.getURI().getPath();
			log.info("拦截到SAAS请求->newUri:{},租户id:{}", newUri,tenantId);
			MultiValueMap<String, String> queryParams = request.getQueryParams();
			log.info("拦截到SAAS请求参数->queryParams:{}", JSONUtils.toJSONString(queryParams));

			MultiValueMap<String, String> encodedQueryParams = encodeQueryParams(queryParams);

			URI updatedUri = UriComponentsBuilder.fromUriString(newUri)
//				.replaceQueryParams(queryParams)
				.queryParams(encodedQueryParams)
				.build(true)
				.toUri();

			ServerHttpRequest newRequest = request.mutate()
				.uri(updatedUri)
				.header(TokenConstant.TENANT_KEY, tenantId)
				.header(TokenConstant.INSIDE_PROXY, TokenConstant.INSIDE_PROXY)
				.build();
			// 处理请求体（适用于 POST、PUT 和 DELETE 请求）
			if (HttpMethod.POST.equals(request.getMethod()) ||
				HttpMethod.PUT.equals(request.getMethod()) ||
				HttpMethod.DELETE.equals(request.getMethod())) {
				log.info("拦截到SAAS请求->POST请求体:{}", request.getBody());
				log.info("拦截到SAAS请求->请求方法:{}", request.getMethod());
				return DataBufferUtils.join(request.getBody())
					.defaultIfEmpty(exchange.getResponse().bufferFactory().wrap(new byte[0]))
					.flatMap(dataBuffer -> {
						byte[] bodyBytes = new byte[dataBuffer.readableByteCount()];
						dataBuffer.read(bodyBytes);
						DataBufferUtils.release(dataBuffer);
						return chain.filter(exchange.mutate()
							.request(generateNewRequest(newRequest, bodyBytes))
							.build());
					});

			}
			return chain.filter(exchange.mutate().request(newRequest).build());
		};
	}

	private ServerHttpRequest generateNewRequest(ServerHttpRequest request, byte[] bodyBytes) {
		// 创建一个新的 DataBuffer
		DataBuffer dataBuffer = createDataBuffer(bodyBytes);
		Flux<DataBuffer> flux = Flux.just(dataBuffer);

		// 使用 ServerHttpRequestDecorator 来重写 getBody 方法
		return new ServerHttpRequestDecorator(request) {
			@Override
			public Flux<DataBuffer> getBody() {
				return flux;
			}
		};
	}

	private DataBuffer createDataBuffer(byte[] bytes) {
		NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
		return nettyDataBufferFactory.wrap(bytes);
	}

	/**
	 * 向拦截器里面传递参数
	 */
	@Data
	public static class Config {

	}

	/**
	 * 获取登录信息
	 *
	 * @param request
	 * @return
	 */
	private Claims getClaims(ServerHttpRequest request) {
		String headerToken = request.getHeaders().getFirst(TokenConstant.HEADER);
		String token = Jwt2Util.getToken(headerToken);
		Claims claims = Jwt2Util.parseJWT(token);
		log.info("拦截到TA请求->Token:{}", token);
		return claims;
	}

	private MultiValueMap<String, String> encodeQueryParams(MultiValueMap<String, String> queryParams) {
		Map<String, List<String>> encodedParams = new HashMap<>();
		queryParams.forEach((key, values) -> {
			List<String> encodedValues = values.stream()
				.map(value -> URLEncoder.encode(value, StandardCharsets.UTF_8))
				.collect(Collectors.toList());
			encodedParams.put(key, encodedValues);
		});
		return new LinkedMultiValueMap<>(encodedParams);
	}
}
