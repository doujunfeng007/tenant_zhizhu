package com.minigod.zero.auth.granter;

import com.minigod.zero.auth.utils.TokenUtil;
import com.minigod.zero.common.cache.CacheNames;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.core.tool.utils.WebUtil;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 图形验证码TokenGranter
 *
 * @author Chill
 */
public class CaptchaTokenGranter extends AbstractTokenGranter {
	/**
	 * 默认验证码
	 */
	private static final String DEFAULT_CAPTCHA = "123456";
	private static final String GRANT_TYPE = "captcha";

	private static final String ENV_VARIABLE = "spring.profiles.active";

	private static final String SIT_ENV_PROFILE = "SIT";

	private AuthenticationManager authenticationManager;

	private ZeroRedis zeroRedis;

	private Environment environment;

	protected CaptchaTokenGranter(AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices,
								  ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory,
								  ZeroRedis zeroRedis, Environment environment) {
		super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
		this.authenticationManager = authenticationManager;
		this.zeroRedis = zeroRedis;
		this.environment = environment;
	}

	@Override
	protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
		HttpServletRequest request = WebUtil.getRequest();
		Map<String, String> parameters = new LinkedHashMap<>(tokenRequest.getRequestParameters());

		// 验证验证码
		validateCaptcha(parameters, request);

		// 提取用户名和密码
		String username = parameters.get("username");
		String password = parameters.get("password");
		parameters.remove("password"); // 防止密码泄露

		// 创建用户认证对象
		Authentication userAuth = createUserAuthentication(username, password, parameters);

		// 创建OAuth2认证对象
		OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
		return new OAuth2Authentication(storedOAuth2Request, userAuth);
	}

	private void validateCaptcha(Map<String, String> parameters, HttpServletRequest request) {
		String captchaKey = StringUtil.isNotBlank(parameters.get("captchaKey")) ? parameters.get("captchaKey") : request.getHeader(TokenUtil.CAPTCHA_HEADER_KEY);
		String captchaCode = StringUtil.isNotBlank(parameters.get("captchaCode")) ? parameters.get("captchaCode") : request.getHeader(TokenUtil.CAPTCHA_HEADER_CODE);

		String redisCode = zeroRedis.get(CacheNames.CAPTCHA_KEY + captchaKey);
		String envCode = environment.getProperty(ENV_VARIABLE);

		boolean isSitEnv = SIT_ENV_PROFILE.equals(envCode.toUpperCase());
		boolean isCaptchaValid = StringUtil.equalsIgnoreCase(redisCode, captchaCode) || (isSitEnv && StringUtil.equalsIgnoreCase(DEFAULT_CAPTCHA, captchaCode));

		if (captchaCode == null || !isCaptchaValid) {
			Boolean result = zeroRedis.del(CacheNames.CAPTCHA_KEY + captchaKey);
			if (result) {
				logger.info("验证码验签【不通过】，删除Redis缓存!");
			}
			throw new OAuth2Exception(TokenUtil.CAPTCHA_NOT_CORRECT);
		} else {
			Boolean result = zeroRedis.del(CacheNames.CAPTCHA_KEY + captchaKey);
			if (result) {
				logger.info("验证码已验证【通过】，删除Redis缓存!");
			}
		}
	}

	private Authentication createUserAuthentication(String username, String password, Map<String, String> details) {
		Authentication userAuth = new UsernamePasswordAuthenticationToken(username, password);
		((AbstractAuthenticationToken) userAuth).setDetails(details);

		try {
			userAuth = authenticationManager.authenticate(userAuth); // 校验密码
		} catch (AccountStatusException | BadCredentialsException ase) {
			throw new InvalidGrantException(ase.getMessage());
		}

		if (userAuth == null || !userAuth.isAuthenticated()) {
			throw new InvalidGrantException("Could not authenticate user: " + username);
		}

		return userAuth;
	}
}
