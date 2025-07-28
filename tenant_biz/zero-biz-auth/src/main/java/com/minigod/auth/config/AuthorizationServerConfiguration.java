/*
 * Copyright (c)  2022. ZSDP Inc. All rights reserved.
 */
package com.minigod.auth.config;

import com.minigod.auth.exception.CustomWebResponseExceptionTranslator;
import com.minigod.auth.exception.OauthResourceAuthenticationEntryPoint;
import com.minigod.auth.granter.MiniGodTokenGranter;
import com.minigod.auth.service.MinigodUserDetailsService;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenEndpointFilter;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * 认证服务器配置
 *
 * @author zsdp
 */
@Order
@Configuration
@AllArgsConstructor
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
	private final DataSource dataSource;

	private final AuthenticationManager authenticationManager;

	private final MinigodUserDetailsService userDetailsService;

	private final TokenStore tokenStore;

	private final TokenEnhancer jwtTokenEnhancer;

	private final JwtAccessTokenConverter jwtAccessTokenConverter;

	private final ZeroRedis minigodRedis;

	@Bean
	@Primary
	public JdbcClientDetailsService jdbcClientDetailsService() {
		return new JdbcClientDetailsService(dataSource);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
		//获取自定义tokenGranter
		TokenGranter tokenGranter = MiniGodTokenGranter.getTokenGranter(authenticationManager, endpoints);

		//配置端点
		endpoints
			.tokenStore(tokenStore)
			.tokenGranter(tokenGranter)
			.tokenEnhancer(jwtTokenEnhancer)
			.userDetailsService(userDetailsService)
			.authenticationManager(authenticationManager)
			.exceptionTranslator(new CustomWebResponseExceptionTranslator());;

		//扩展token返回结果
		if (jwtAccessTokenConverter != null && jwtTokenEnhancer != null) {
			TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
			List<TokenEnhancer> enhancerList = new ArrayList<>();
			enhancerList.add(jwtTokenEnhancer);
			enhancerList.add(jwtAccessTokenConverter);
			tokenEnhancerChain.setTokenEnhancers(enhancerList);
			//jwt增强
			endpoints.tokenEnhancer(tokenEnhancerChain).accessTokenConverter(jwtAccessTokenConverter);
		}
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
		ClientCredentialsTokenEndpointFilter filter = new ClientCredentialsTokenEndpointFilter();
		filter.setAuthenticationEntryPoint(new OauthResourceAuthenticationEntryPoint());
		oauthServer
			.allowFormAuthenticationForClients()
			.tokenKeyAccess("permitAll()")
			.checkTokenAccess("permitAll()")
			.addTokenEndpointAuthenticationFilter(filter);
	}

}
