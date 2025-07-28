package com.minigod.auth.granter.subaccount;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/12/2 13:32
 * @description： 子账号登录
 */
public class SubAccountAuthenticationGranter extends AbstractTokenGranter {

	private static final String GRANT_TYPE = "sub_account";

	private final AuthenticationManager authenticationManager;


	public SubAccountAuthenticationGranter(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, AuthenticationManager authenticationManager) {
		super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
		this.authenticationManager = authenticationManager;
	}


	@Override
	protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
		Map<String, String> parameters = new LinkedHashMap<String, String>(tokenRequest.getRequestParameters());
		String subAccount = parameters.get("subAccount");
		String mainAccount = parameters.get("mainAccount");
		String password = parameters.get("password");

		parameters.remove("password");

		Authentication authenticationToken = new SubAccountAuthenticationToken(subAccount,password,mainAccount);
		((AbstractAuthenticationToken) authenticationToken).setDetails(parameters);
		try {
			authenticationToken = authenticationManager.authenticate(authenticationToken);
		}
		catch (AccountStatusException | BadCredentialsException ase) {
			//covers expired, locked, disabled cases (mentioned in section 5.2, draft 31)
			throw new InvalidGrantException(ase.getMessage());
		}
		// If the username/password are wrong the spec says we should send 400/invalid grant

		if (authenticationToken == null || !authenticationToken.isAuthenticated()) {
			throw new InvalidGrantException("Could not authenticate user: " + subAccount);
		}

		OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
		return new OAuth2Authentication(storedOAuth2Request, authenticationToken);

	}
}
