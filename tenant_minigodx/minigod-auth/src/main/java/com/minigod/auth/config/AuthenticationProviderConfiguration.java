package com.minigod.auth.config;

import com.minigod.auth.granter.captcha.CaptchaAuthenticationProvider;
import com.minigod.auth.granter.password.PasswordAuthenticationProvider;
import com.minigod.auth.granter.subaccount.SubAccountAuthenticationProvider;
import com.minigod.auth.service.AuthorizationServicesUserDetails;
import com.minigod.auth.service.MinigodUserDetailsService;
import com.minigod.auth.support.MiniGodPasswordEncoderFactories;
import com.minigod.auth.utils.AccountLoginUtils;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/8/2 15:18
 * @description：
 */
@Configuration
@Order(Ordered.LOWEST_PRECEDENCE - 5000 - 101)
public class AuthenticationProviderConfiguration extends GlobalAuthenticationConfigurerAdapter {

	@Autowired
	private ZeroRedis minigodRedis;

	@Autowired
	private AccountLoginUtils accountLoginUtils;

	@Autowired
	private MinigodUserDetailsService miniGodUserDetailsService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return MiniGodPasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(captchaAuthenticationProvider())
			.authenticationProvider(passwordAuthenticationProvider())
			.authenticationProvider(subAccountAuthenticationProvider());
	}

	public CaptchaAuthenticationProvider captchaAuthenticationProvider(){
		return new CaptchaAuthenticationProvider(miniGodUserDetailsService,minigodRedis);
	}

	public PasswordAuthenticationProvider passwordAuthenticationProvider(){
		PasswordAuthenticationProvider passwordAuthenticationProvider = new PasswordAuthenticationProvider();
		passwordAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		passwordAuthenticationProvider.setAccountLoginUtils(accountLoginUtils);
		passwordAuthenticationProvider.setUserDetailsService(miniGodUserDetailsService);
		return passwordAuthenticationProvider;
	}

	public SubAccountAuthenticationProvider subAccountAuthenticationProvider(){
		SubAccountAuthenticationProvider subAccountAuthenticationProvider =
			new SubAccountAuthenticationProvider(miniGodUserDetailsService,accountLoginUtils);
		return subAccountAuthenticationProvider;
	}
}
