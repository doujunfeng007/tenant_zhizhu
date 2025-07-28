
package com.minigod.zero.auth.granter;

import com.minigod.zero.auth.service.ZeroUserDetailsService;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.social.props.SocialProperties;
import com.minigod.zero.platform.utils.CheckCaptchaCache;
import com.minigod.zero.user.feign.IUserClient;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 自定义拓展TokenGranter
 *
 * @author Chill
 */
public class ZeroTokenGranter {

	/**
	 * 自定义tokenGranter
	 */
	public static TokenGranter getTokenGranter(final AuthenticationManager authenticationManager, final ZeroUserDetailsService userDetailsService, final AuthorizationServerEndpointsConfigurer endpoints,
											   ZeroRedis zeroRedis, Environment environment, IUserClient userClient, CheckCaptchaCache checkCaptchaCache, SocialProperties socialProperties) {
		// 默认tokenGranter集合
		List<TokenGranter> granters = new ArrayList<>(Collections.singletonList(endpoints.getTokenGranter()));
		// 增加图形验证码模式
		granters.add(new CaptchaTokenGranter(authenticationManager, endpoints.getTokenServices(), endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory(), zeroRedis, environment));
		// 增加短信验证码登录模式
		granters.add(new SmsCodeTokenGranter(userDetailsService, endpoints.getTokenServices(), endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory(), zeroRedis));
		// 增加第三方登录模式
		granters.add(new SocialTokenGranter(endpoints.getTokenServices(), endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory(), userClient, socialProperties));
		// 组合tokenGranter集合
		return new CompositeTokenGranter(granters);
	}

}
