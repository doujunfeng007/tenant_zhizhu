package com.minigod.auth.config;

import com.minigod.auth.converter.JwtAuthenticationConverter;
import com.minigod.auth.exception.OauthResourceAuthenticationEntryPoint;
import com.minigod.auth.extractor.ZeroAuthTokenExtractor;
import com.minigod.zero.core.jwt.props.JwtProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.security.jwt.crypto.sign.SignatureVerifier;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * 资源服务配置
 * @author guangjie.liao
 * @date 2022/11/18 08:21
 */
@Slf4j
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	@Autowired
	private JwtProperties jwtProperties;


    @Value("${spring.application.name}")
    private String serverName;


	@Bean
	public TokenStore jwtTokenStore() {
		return new JwtTokenStore(getJwtAccessTokenConverter(jwtProperties));
	}

	/**
	 * 用于生成jwt
	 */
	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		return getJwtAccessTokenConverter(jwtProperties);
	}


	/**
	 * 自定义 JwtAccessTokenConverter
	 */
	private JwtAccessTokenConverter getJwtAccessTokenConverter(JwtProperties jwtProperties) {
		JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
		DefaultAccessTokenConverter defaultAccessTokenConverter = new DefaultAccessTokenConverter();
		defaultAccessTokenConverter.setUserTokenConverter(new JwtAuthenticationConverter());
		accessTokenConverter.setAccessTokenConverter(defaultAccessTokenConverter);
		accessTokenConverter.setSigningKey(jwtProperties.getSignKey());
		SignatureVerifier verifier = new MacSigner(jwtProperties.getSignKey());
		accessTokenConverter.setVerifier(verifier);
		return accessTokenConverter;
	}

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(serverName);
		resources.tokenStore(jwtTokenStore());
		resources.tokenExtractor(new ZeroAuthTokenExtractor());
        resources.authenticationEntryPoint(new OauthResourceAuthenticationEntryPoint());
    }

	@Override
	public void configure(HttpSecurity http) throws Exception {

        // 资源链路
		// 资源链路
		http
			.csrf().disable()
			//不通过session获取SecurityContext
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and().authorizeRequests().antMatchers("/oauth/**","/register","/actuator/**","/proxy/**").permitAll()
			// 其他请求都需认证
			.and().authorizeRequests().anyRequest().authenticated()
			.and().cors();
    }
}


