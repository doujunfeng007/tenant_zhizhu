package com.minigod.zero.gateway.filter;

import io.netty.buffer.ByteBufAllocator;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.*;

/**
 * <p>
 * 全局拦截器，作用所有的微服务
 * <p>
 * 1. 对请求头中参数进行处理 from 参数进行清洗
 * 2. 重写StripPrefix = 1,支持全局
 *
 * @author lengleng
 */
@Component
public class RequestFilter implements GlobalFilter, Ordered {

	/**
	 * Process the Web request and (optionally) delegate to the next
	 * {@code WebFilter} through the given {@link GatewayFilterChain}.
	 *
	 * @param exchange the current server exchange
	 * @param chain    provides a way to delegate to the next filter
	 * @return {@code Mono<Void>} to indicate when request processing is complete
	 */
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		// 1. 清洗请求头中from 参数
		ServerHttpRequest request = exchange.getRequest().mutate()
			.headers(httpHeaders -> httpHeaders.remove("X"))
			.build();

		// 2. 重写StripPrefix
		addOriginalRequestUrl(exchange, request.getURI());
		String rawPath = request.getURI().getRawPath();
		String newPath = "/" + Arrays.stream(StringUtils.tokenizeToStringArray(rawPath, "/"))
			.skip(1L).collect(Collectors.joining("/"));
		ServerHttpRequest newRequest = request.mutate()
			.path(newPath)
			.build();
		exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, newRequest.getURI());

		// 3. 缓存Body
		MediaType mediaType = exchange.getRequest().getHeaders().getContentType();
		if (HttpMethod.POST.equals(newRequest.getMethod()) && null != mediaType
			&& (mediaType.isCompatibleWith(MediaType.APPLICATION_JSON) || mediaType.isCompatibleWith(MediaType.APPLICATION_FORM_URLENCODED))) {
			return DataBufferUtils.join(newRequest.getBody()).map(dataBuffer -> {
				byte[] bytes = new byte[dataBuffer.readableByteCount()];
				dataBuffer.read(bytes);
				DataBufferUtils.release(dataBuffer);
				return bytes;
			}).flatMap(bodyBytes -> {
				String msg = new String(bodyBytes, StandardCharsets.UTF_8);
				exchange.getAttributes().put(CACHED_REQUEST_BODY_ATTR, msg);
				return chain.filter(exchange.mutate().request(generateNewRequest(newRequest, bodyBytes)).build());
			});
		}
		return chain.filter(exchange.mutate().request(newRequest.mutate().build()).build());
	}

	private ServerHttpRequest generateNewRequest(ServerHttpRequest request, byte[] bytes) {
		URI ex = UriComponentsBuilder.fromUri(request.getURI()).build(true).toUri();
		ServerHttpRequest newRequest = request.mutate().uri(ex).build();
		DataBuffer dataBuffer = stringBuffer(bytes);
		Flux<DataBuffer> flux = Flux.just(dataBuffer);
		newRequest = new ServerHttpRequestDecorator(newRequest) {
			@Override
			public Flux<DataBuffer> getBody() {
				return flux;
			}
		};
		return newRequest;
	}

	private DataBuffer stringBuffer(byte[] bytes) {
		NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
		return nettyDataBufferFactory.wrap(bytes);
	}

	@Override
	public int getOrder() {
		return -1000;
	}

}
