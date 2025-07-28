/*
 * Copyright (c)  2022. ZSDP Inc. All rights reserved.
 */
package com.minigod.auth.config;

import com.minigod.auth.events.publisher.OauthAuthenticationEventPublisher;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Security配置
 *
 * @author zsdp
 */
@Configuration
@EnableWebSecurity
@AllArgsConstructor
@Order(Ordered.LOWEST_PRECEDENCE - 4999)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Bean
	@Override
	@SneakyThrows
	public AuthenticationManager authenticationManagerBean() {
		return super.authenticationManagerBean();
	}

	@Override
	@SneakyThrows
	protected void configure(HttpSecurity http) {
		http
			//必须配置，不然OAuth2的http配置不生效
			.requestMatchers()
			.antMatchers("/auth/**","/oauth/**","/actuator/**","/proxy/auth/token")
			.and()
			.authorizeRequests()
			// 自定义页面或处理url是，如果不配置全局允许，浏览器会提示服务器将页面转发多次
			.antMatchers("/auth/login", "/auth/authorize")
			.permitAll()
			.anyRequest()
			.authenticated();
		http.headers().frameOptions().disable();
		http.httpBasic().and().csrf().disable();
	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/js/*.js", "/css/*.css");
	}


	@Bean
	public OauthAuthenticationEventPublisher authenticationEventPublisher(ApplicationEventPublisher publisher) {
		return new OauthAuthenticationEventPublisher(publisher);
	}
}
