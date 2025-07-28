package com.minigod.zero.gateway.config;

import com.minigod.zero.gateway.props.AuthProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * 路由配置信息
 *
 * @author Chill
 */
@Configuration(proxyBeanMethods = false)
@AllArgsConstructor
@EnableConfigurationProperties({AuthProperties.class})
@Slf4j
public class RouterFunctionConfiguration {

	/**
	 * 指定允许的域名和端口列表
	 */
	private static final String ALLOWED_ORIGINS = "http://zero-api.zszhizhu.com/," +
		"http://zero-api.zszhizhu.com:1888/," +
		"http://h5-sec.zszhizhu.com:1888/,http://h5-sec.zszhizhu.com:1888,http://cloud-api.zsdp.zszhizhu.com:88," +
			"http://cloud-api.zsdp.zszhizhu.com:7001," +
			"http://cloud-api.ifund.live:31076,http://mkt-api.ifund.live:31071,"+
		"http://cloud-api.ifund.live:688,http://cloud-api.ifund.live:689,http://cloud-api.ifund.live:691,http://cloud-api.ifund.live:692";
	/**
	 * 这里为支持的请求头，如果有自定义的header字段请自己添加
	 * content-type,devicecode,devicetype,ostype,osversion,source-type,zero-auth
	 */
	private static final String ALLOWED_HEADERS = "X-Requested-With,Tenant-Id,Accept-Language,Source-Type,Zero-Auth,quotToken,Content-Type,Authorization,Sign,TS,deviceCode,osType,deviceType,osVersion,credential,X-XSRF-TOKEN,token,username,client,knfie4j-gateway-request,knife4j-gateway-code,request-origion,x-channel,tenantToken";
	private static final String ALLOWED_METHODS = "GET,POST,PUT,DELETE,OPTIONS,HEAD";
	private static final String ALLOWED_EXPOSE = "*";
	private static final String MAX_AGE = "18000L";

	/**
	 * 跨域配置
	 */
	@Bean
	public WebFilter corsFilter() {
		return (ServerWebExchange ctx, WebFilterChain chain) -> {
			ServerHttpRequest request = ctx.getRequest();
			if (CorsUtils.isCorsRequest(request)) {
				ServerHttpResponse response = ctx.getResponse();
				HttpHeaders headers = response.getHeaders();
				// 检查请求的Origin是否在允许的域名和端口列表中
				String origin = request.getHeaders().getOrigin();
				log.info("aaaaa"+ALLOWED_ORIGINS);
				if (StringUtils.isNotBlank(origin) && ALLOWED_ORIGINS.contains(origin)) {
					log.info("origin======"+origin);
					headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, origin);
				}
//				headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, ALLOWED_HEADERS);
				headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, ALLOWED_HEADERS);
				headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, ALLOWED_METHODS);
				headers.add(HttpHeaders.ACCESS_CONTROL_MAX_AGE, MAX_AGE);
				headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, ALLOWED_EXPOSE);
				headers.set(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
				if (request.getMethod() == HttpMethod.OPTIONS) {
					response.setStatusCode(HttpStatus.OK);
					return Mono.empty();
				}
			}
			return chain.filter(ctx);
		};
	}

}
