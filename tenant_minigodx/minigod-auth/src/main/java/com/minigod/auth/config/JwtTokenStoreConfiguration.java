/*
 * Copyright (c)  2022. ZSDP Inc. All rights reserved.
 */
package com.minigod.auth.config;

import com.minigod.auth.support.MiniGodJwtTokenEnhancer;
import com.minigod.zero.core.jwt.props.JwtProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * JwtTokenStore
 *
 * @author zsdp
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(prefix = "minigod.security.oauth2", name = "storeType", havingValue = "jwt", matchIfMissing = true)
public class JwtTokenStoreConfiguration {

	/**
	 * 用于扩展jwt
	 */
	@Bean
	@ConditionalOnMissingBean(name = "jwtTokenEnhancer")
	public TokenEnhancer jwtTokenEnhancer(JwtAccessTokenConverter jwtAccessTokenConverter, JwtProperties jwtProperties) {
		return new MiniGodJwtTokenEnhancer(jwtAccessTokenConverter, jwtProperties);
	}

}
