package com.minigod.zero.gateway.filter;

import com.minigod.zero.core.jwt.Jwt2Util;
import com.minigod.zero.core.launch.constant.TokenConstant;
import io.jsonwebtoken.Claims;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/9/19 11:22
 * @description：ta系统局部拦截器
 */
@Slf4j
@Component
public class TaGatewayFilterFactory extends AbstractGatewayFilterFactory<TaGatewayFilterFactory.Config> {
	/**
	 * 用户id
	 */
	private static final String CUST_ID = "custId";
	/**
	 * 理财账号
	 */
	private static final String ACCOUNT_ID = "accountId";
	/**
	 * 用户名称/机构名称
	 */
	private static final String CUST_NAME = "custName";
	/**
	 * 角色标识
	 */
	private static final String ROLE_ID = "roleId";

	public TaGatewayFilterFactory() {
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest().mutate().build();
			Claims claims = getClaims(request);
			Object custId = claims.get(TokenConstant.CUST_ID);
			Object accountId = claims.get(TokenConstant.ACCOUNT);
			Object custName = claims.get(TokenConstant.REAL_NAME);
			Object roleId = claims.get(TokenConstant.ROLE_ID);
			log.info("拦截到TA请求->custId:{},accountId:{},custName:{},roleId:{}", custId, accountId, custName, roleId);
			request.mutate().header(CUST_ID, String.valueOf(custId))
				.header(ACCOUNT_ID, accountId == null ? null : String.valueOf(accountId))
				.header(CUST_NAME, custName == null ? null : String.valueOf(custName))
				.header(ROLE_ID, roleId == null ? null : String.valueOf(roleId))
				.build();
			return chain.filter(exchange.mutate().request(request).build());
		};
	}

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
		return claims;
	}
}
