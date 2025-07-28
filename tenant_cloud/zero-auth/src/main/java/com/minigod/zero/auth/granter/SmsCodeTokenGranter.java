package com.minigod.zero.auth.granter;

import com.alibaba.fastjson.JSONObject;
import com.minigod.zero.auth.factorys.OperatingEnvironment;
import com.minigod.zero.auth.service.LoginContext;
import com.minigod.zero.auth.service.ZeroUserDetails;
import com.minigod.zero.auth.service.ZeroUserDetailsService;
import com.minigod.zero.common.cache.CacheNames;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.tool.exception.ZeroException;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.platform.enums.EmailTemplate;
import com.minigod.zero.platform.enums.SmsTemplate;
import com.minigod.zero.platform.utils.EmailUtil;
import com.minigod.zero.platform.utils.SmsUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.web.server.ServerWebInputException;

import java.util.LinkedHashMap;

/**
 * 短信验证码TokenGranter
 *
 * @author Chill
 */
public class SmsCodeTokenGranter extends AbstractTokenGranter {

	private static final String GRANT_TYPE = "sms_code";
	private final ZeroRedis zeroRedis;

	private ZeroUserDetailsService userDetailsService;

	@Value("${sms.enabled}")
	private Boolean isMsgSending;

	protected SmsCodeTokenGranter(ZeroUserDetailsService userDetailsService,
								  AuthorizationServerTokenServices tokenServices,
								  ClientDetailsService clientDetailsService,
								  OAuth2RequestFactory requestFactory,
								  ZeroRedis zeroRedis) {
		super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
		this.userDetailsService = userDetailsService;
		this.zeroRedis = zeroRedis;
	}

	@Override
	protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
		LoginContext loginContext = new LoginContext(new LinkedHashMap<>(tokenRequest.getRequestParameters()));
		loginContext.paramCheck();

		validateUserVerificationCode(loginContext);
		ZeroUserDetails userDetails = authenticateUser(loginContext);

		Authentication userAuth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		OAuth2Request storedOAuth2Request = createOAuth2Request(client, tokenRequest);
		String userKey = String.format(CacheNames.USER_VERIFICATION_CODE, loginContext.getTenantId(), loginContext.getUserName());
		zeroRedis.del(userKey);
		return new OAuth2Authentication(storedOAuth2Request, userAuth);
	}

	private OAuth2Request createOAuth2Request(ClientDetails client, TokenRequest tokenRequest) {
		return new OAuth2Request(
			tokenRequest.getRequestParameters(),
			client.getClientId(),
			client.getAuthorities(),
			true,
			tokenRequest.getScope(),
			client.getResourceIds(),
			null,
			null,
			null);
	}

	private void validateUserVerificationCode(LoginContext loginContext) {
		String userKey = String.format(CacheNames.USER_VERIFICATION_CODE, loginContext.getTenantId(), loginContext.getUserName());
		String redisCaptchaCode = zeroRedis.get(userKey);

		if (StringUtil.isBlank(redisCaptchaCode)) {
			throw new ServerWebInputException("用户未经验证");
		}
		logger.info("缓存验证码："+redisCaptchaCode+" 输入验证码："+loginContext.getCaptchaCode());
//		if (!loginContext.getCaptchaCode().equals(redisCaptchaCode)) {
//			throw new ServerWebInputException("非法验证码");
//		}
		if (OperatingEnvironment.openTransactionPassword() && !loginContext.getCaptchaCode().equals(redisCaptchaCode)) {
			throw new ServerWebInputException("非法验证码");
		}
	}

	private ZeroUserDetails authenticateUser(LoginContext loginContext) {
		logger.info("登录信息：{}"+ JSONObject.toJSONString(loginContext));
		if (loginContext.isEmail()) {
			return authenticateByEmail(loginContext);
		} else {
			return authenticateByPhone(loginContext);
		}

	}

	private ZeroUserDetails authenticateByEmail(LoginContext loginContext) {
		boolean isValid = EmailUtil.builder()
			.captchaKey(loginContext.getCaptchaKey())
			.captchaCode(loginContext.getCaptchaCode())
			.templateCode(EmailTemplate.INTEGRATED_MANAGEMENT_BACKEND.getCode())
			.validate();

		if (OperatingEnvironment.openTransactionPassword() &&!isValid) {
			throw new ServerWebInputException("验证码不正确");
		}

		return loadUserDetails(loginContext);
	}

	private ZeroUserDetails authenticateByPhone(LoginContext loginContext) {
		boolean isValid = SmsUtil.builder()
			.templateCode(SmsTemplate.LOGIN_VERIFICATION_CODE.getCode())
			.captchaKey(loginContext.getCaptchaKey())
			.captchaCode(loginContext.getCaptchaCode())
			.areaCode(loginContext.getAreaCode())
			.phoneNumber(loginContext.getLoginAccount())
			.validate();

		if (OperatingEnvironment.openTransactionPassword() && !isValid) {
			throw new ServerWebInputException("验证码不正确");
		}

		return loadUserDetails(loginContext);
	}

	private ZeroUserDetails loadUserDetails(LoginContext loginContext) {
		ZeroUserDetails userDetails = null;
		if (loginContext.isEmail()) {
			userDetails = userDetailsService.loadUserByEmail(loginContext.getTenantId(), loginContext.getLoginAccount());
		} else {
			userDetails = userDetailsService.loadUserByPhone(loginContext.getTenantId(), loginContext.getLoginAccount(), loginContext.getAreaCode());
		}
		if (userDetails == null) {
			throw new ZeroException("用户未绑定此账号：" + loginContext.getLoginAccount());
		}

		return userDetails;
	}

}
