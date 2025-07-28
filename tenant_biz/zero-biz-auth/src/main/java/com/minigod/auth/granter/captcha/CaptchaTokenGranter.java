package com.minigod.auth.granter.captcha;

import com.minigod.zero.core.launch.constant.TokenConstant;
import com.minigod.zero.core.tool.utils.WebUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 验证码TokenGranter
 *
 * @author zsdp
 */
public class CaptchaTokenGranter extends AbstractTokenGranter {

	private static final String GRANT_TYPE = "captcha";

	private final AuthenticationManager authenticationManager;



	public CaptchaTokenGranter(AuthenticationManager authenticationManager,
							   AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
		this(authenticationManager, tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
	}

	protected CaptchaTokenGranter(AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices,
												ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, String grantType) {
		super(tokenServices, clientDetailsService, requestFactory, grantType);
		this.authenticationManager = authenticationManager;
	}

	@Override
	protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
		Map<String, String> parameters = new LinkedHashMap<String, String>(tokenRequest.getRequestParameters());

		String code = parameters.get("code");
		String username = parameters.get("username");
		String areaCode = parameters.get("areaCode");
		String tenantId = parameters.get("tenantId");
		String captchaKey = parameters.get("captchaKey");
		String packageId = parameters.get("packageId");
		Long channelId = null;
		Long brokerId= null;
		if (!ObjectUtils.isEmpty(parameters.get("channelId"))) {
			channelId = Long.valueOf(parameters.get("channelId"));
		}
		if (!ObjectUtils.isEmpty(parameters.get("brokerId"))) {
			brokerId = Long.valueOf(parameters.get("brokerId"));
		}
		HttpServletRequest request = WebUtil.getRequest();
		if (!StringUtils.hasLength(tenantId) && request != null) {
			tenantId = request.getHeader(TokenConstant.TENANT_KEY);
		}
		parameters.remove("code");
		Authentication userAuth = new CaptchaAuthenticationToken(username, code,areaCode,tenantId,captchaKey,packageId,channelId,brokerId);
		((AbstractAuthenticationToken) userAuth).setDetails(parameters);
		try {
			userAuth = authenticationManager.authenticate(userAuth);
		}
		catch (AccountStatusException | BadCredentialsException ase) {
			//covers expired, locked, disabled cases (mentioned in section 5.2, draft 31)
			throw new InvalidGrantException(ase.getMessage());
		}
		// If the username/password are wrong the spec says we should send 400/invalid grant

		if (userAuth == null || !userAuth.isAuthenticated()) {
			throw new InvalidGrantException("Could not authenticate user: " + username);
		}

		OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
		return new OAuth2Authentication(storedOAuth2Request, userAuth);
	}
}
