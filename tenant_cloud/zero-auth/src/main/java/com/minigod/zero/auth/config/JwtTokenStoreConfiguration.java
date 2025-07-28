
package com.minigod.zero.auth.config;

import com.minigod.zero.auth.support.ZeroJwtTokenEnhancer;
import com.minigod.zero.core.jwt.props.JwtProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.security.jwt.crypto.sign.SignatureVerifier;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * JwtTokenStore
 *
 * @author Chill
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(prefix = "zero.security.oauth2", name = "storeType", havingValue = "jwt", matchIfMissing = true)
public class JwtTokenStoreConfiguration {

	/**
	 * 使用jwtTokenStore存储token
	 */
	@Bean
	public TokenStore jwtTokenStore(JwtProperties jwtProperties) {
		return new JwtTokenStore(getJwtAccessTokenConverter(jwtProperties));
	}

	/**
	 * 用于生成jwt
	 */
	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter(JwtProperties jwtProperties) {
		return getJwtAccessTokenConverter(jwtProperties);
	}

	/**
	 * 自定义 JwtAccessTokenConverter
	 */
	private JwtAccessTokenConverter getJwtAccessTokenConverter(JwtProperties jwtProperties) {
		JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
		accessTokenConverter.setSigningKey(jwtProperties.getSignKey());
		SignatureVerifier verifier = new MacSigner(jwtProperties.getSignKey());
		accessTokenConverter.setVerifier(verifier);
		return accessTokenConverter;
	}

	/**
	 * 用于扩展jwt
	 */
	@Bean
	@ConditionalOnMissingBean(name = "jwtTokenEnhancer")
	public TokenEnhancer jwtTokenEnhancer(JwtAccessTokenConverter jwtAccessTokenConverter, JwtProperties jwtProperties) {
		return new ZeroJwtTokenEnhancer(jwtAccessTokenConverter, jwtProperties);
	}

}
