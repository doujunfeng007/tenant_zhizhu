
package com.minigod.zero.biz.config;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * 自定义资源放行
 *
 * @author Chill
 */
@Configuration(proxyBeanMethods = false)
@AllArgsConstructor
public class ZeroResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	@Override
	@SneakyThrows
	public void configure(HttpSecurity http) {
		http.authorizeRequests()
			.antMatchers(
				"/actuator/**",
				"/open/oauth/captcha",
				"/open/oauth/logout",
				"/open/oauth/clear-cache",
				"/open/oauth/render/**",
				"/open/oauth/callback/**",
				"/open/oauth/revoke/**",
				"/open/oauth/refresh/**",
				"/open/oauth/login",
				"/oauth/authorize/**",
				"/oauth/form",
				"/token/**",
				"/mobile/**",
				"/static/**",
				"/v2/api-docs").permitAll()
			.anyRequest().authenticated().and()
			.csrf().disable();
	}

}
