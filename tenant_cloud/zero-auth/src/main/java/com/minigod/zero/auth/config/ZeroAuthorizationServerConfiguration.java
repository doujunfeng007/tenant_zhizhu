package com.minigod.zero.auth.config;

import com.minigod.zero.auth.constant.AuthConstant;
import com.minigod.zero.auth.service.ZeroClientDetailsServiceImpl;
import com.minigod.zero.auth.service.ZeroUserDetailsServiceImpl;
import com.minigod.zero.auth.granter.ZeroTokenGranter;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.social.props.SocialProperties;
import com.minigod.zero.platform.utils.CheckCaptchaCache;
import com.minigod.zero.user.feign.IUserClient;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.TokenGranter;
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
 * @author Chill
 */
@Order
@Configuration(proxyBeanMethods = false)
@AllArgsConstructor
@EnableAuthorizationServer
public class ZeroAuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

	private final DataSource dataSource;

	private final AuthenticationManager authenticationManager;

	private final ZeroUserDetailsServiceImpl userDetailsService;

	private final TokenStore tokenStore;

	private final TokenEnhancer jwtTokenEnhancer;

	private final JwtAccessTokenConverter jwtAccessTokenConverter;

	private final ZeroRedis zeroRedis;

	private final Environment environment;

	private final IUserClient userClient;

	private final CheckCaptchaCache checkCaptchaCache;

	private final SocialProperties socialProperties;


	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
		//获取自定义tokenGranter
		TokenGranter tokenGranter = ZeroTokenGranter.getTokenGranter(authenticationManager, userDetailsService, endpoints, zeroRedis, environment, userClient, checkCaptchaCache, socialProperties);

		//配置后台登录端点
		endpoints.tokenStore(tokenStore)
			.authenticationManager(authenticationManager)
			.userDetailsService(userDetailsService)
			.tokenGranter(tokenGranter)
			.pathMapping("/oauth/token", "/back/oauth/token");

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

	/**
	 * 配置客户端信息
	 */
	@Override
	@SneakyThrows
	public void configure(ClientDetailsServiceConfigurer clients) {
		ZeroClientDetailsServiceImpl clientDetailsService = new ZeroClientDetailsServiceImpl(dataSource);
		clientDetailsService.setSelectClientDetailsSql(AuthConstant.DEFAULT_SELECT_STATEMENT);
		clientDetailsService.setFindClientDetailsSql(AuthConstant.DEFAULT_FIND_STATEMENT);
		clients.withClientDetails(clientDetailsService);
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
		oauthServer
			.allowFormAuthenticationForClients()
			.tokenKeyAccess("permitAll()")
			.checkTokenAccess("isAuthenticated()");
	}

}
