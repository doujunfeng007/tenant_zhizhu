
package com.minigod.zero.biz.granter;

import com.minigod.zero.cms.feign.oms.ILanguageClient;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.social.props.SocialProperties;
import com.minigod.zero.cust.feign.ICustAuthClient;
import com.minigod.zero.platform.utils.CheckCaptchaCache;
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
	public static TokenGranter getTokenGranter(final UserDetailsService userDetailsService, final AuthorizationServerEndpointsConfigurer endpoints, ZeroRedis zeroRedis,
											   CheckCaptchaCache checkCaptchaCache, ICustAuthClient custAuthClient, SocialProperties socialProperties, ILanguageClient languageClient) {
		// 默认tokenGranter集合
		List<TokenGranter> granters = new ArrayList<>(Collections.singletonList(endpoints.getTokenGranter()));
		// 增加手机验证码注册登录
		granters.add(new RegisterTokenGranter(userDetailsService, endpoints.getTokenServices(), endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory(), checkCaptchaCache, custAuthClient, languageClient));
		// 增加密码登录模式
		granters.add(new PasswordTokenGranter(userDetailsService, endpoints.getTokenServices(), endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory(), custAuthClient));
		// 增加授权人登录模式
		granters.add(new CompanyTokenGranter(userDetailsService, endpoints.getTokenServices(), endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory(), custAuthClient));
		// 交易账号密码登录
		granters.add(new AccountTokenGranter(userDetailsService, endpoints.getTokenServices(), endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory(), zeroRedis, custAuthClient));
		// 增加ESOP登录模式
		granters.add(new EsopTokenGranter(userDetailsService, endpoints.getTokenServices(), endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory(), custAuthClient));
		// 增加第三方登录模式
		granters.add(new SocialTokenGranter(userDetailsService, endpoints.getTokenServices(), endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory(), custAuthClient, socialProperties));
		// 组合tokenGranter集合
		return new CompositeTokenGranter(granters);
	}

}
