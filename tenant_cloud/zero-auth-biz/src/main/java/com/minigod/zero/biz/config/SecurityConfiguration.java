package com.minigod.zero.biz.config;

import com.minigod.zero.biz.support.ZeroPasswordEncoderFactories;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Security配置
 *
 * @author Chill
 */
@Configuration(proxyBeanMethods = false)
@AllArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter { // 过时类待优化

	@Bean
	@Override
	@SneakyThrows
	public AuthenticationManager authenticationManagerBean() {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return ZeroPasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Override
	@SneakyThrows
	protected void configure(HttpSecurity http) {
		http.headers().frameOptions().disable();
		http.httpBasic().and().csrf().disable();
		http.formLogin().loginPage("/open/oauth/login").loginProcessingUrl("/oauth/form");
	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/js/*.js", "/css/*.css");
	}

	/**
	@Bean
	public SecurityFilterChain filterChain(@NotNull HttpSecurity httpSecurity) throws Exception {
		httpSecurity.headers().frameOptions().disable();
		return httpSecurity.formLogin() //自定义页面
			.loginPage("/oauth/login")  //登陆界面
			.loginProcessingUrl("/oauth/form") //登陆访问路径：提交表单之后跳转的地址,可以看作一个中转站，这个步骤就是验证user的一个过程
			//.defaultSuccessUrl("/test/index",true).permitAll() //登陆成功之后跳转的路径
			.and().authorizeRequests()
			//.antMatchers("/","/test/hello","/user/login").permitAll() //匹配的路径不需要认证
			//.antMatchers("/test/index").hasAuthority("admin")
			.anyRequest().authenticated()
			.and().csrf().disable()
			.build();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		// 拦截不处理的请求，预防网络攻击
		return (web) -> web.ignoring().antMatchers("/js/*.js", "/css/*.css");
	}
	 */

}
