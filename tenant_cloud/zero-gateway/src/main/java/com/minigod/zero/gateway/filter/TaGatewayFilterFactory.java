package com.minigod.zero.gateway.filter;

import com.minigod.zero.core.jwt.Jwt2Util;
import com.minigod.zero.core.launch.constant.TokenConstant;
import io.jsonwebtoken.Claims;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/9/19 11:22
 * @description：ta，cfund系统局部拦截器 这个拦截器会在AuthFilter拦截器之后进行拦截
 * 所以，进到这里都是经过登录校验的请求
 */
@Slf4j
@Component
public class TaGatewayFilterFactory extends AbstractGatewayFilterFactory<TaGatewayFilterFactory.Config> {
	/**
	 * 用户id
	 */
	private static final String USER_NAME = "userName";
	/**
	 * 用户id
	 */
	private static final String USERID = "userId";


	public TaGatewayFilterFactory() {
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest().mutate().build();
			Claims claims = getClaims(request);
			Object userName = claims.get(TokenConstant.ACCOUNT);
			Object userId = claims.get(TokenConstant.USER_ID);
			log.info("拦截到TA请求->userName:{},userId:{}", userName, userId);
			request.mutate().header(USER_NAME, String.valueOf(userName))
				.header(USERID, userId == null ? null : String.valueOf(userId))
				.build();
			return chain.filter(exchange.mutate().request(request).build());
		};
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
}
