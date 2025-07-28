package com.minigod.auth.granter.subaccount;

import com.minigod.auth.exception.PasswordErrorException;
import com.minigod.auth.service.AppUserDetails;
import com.minigod.auth.service.AuthorizationServicesUserDetails;
import com.minigod.auth.service.MinigodUserDetailsService;
import com.minigod.auth.utils.AccountLoginUtils;
import com.minigod.auth.utils.RSANewUtil;
import com.minigod.common.constant.CommonConstant;
import com.minigod.zero.core.i18n.utils.I18nUtil;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/12/2 13:32
 * @description：
 */
public class SubAccountAuthenticationProvider implements AuthenticationProvider {

	private AccountLoginUtils accountLoginUtils;
	private MinigodUserDetailsService userDetailsService;

	public SubAccountAuthenticationProvider(MinigodUserDetailsService userDetailsService, AccountLoginUtils accountLoginUtils){
		this.userDetailsService = userDetailsService;
		this.accountLoginUtils = accountLoginUtils;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		SubAccountAuthenticationToken authenticationToken = (SubAccountAuthenticationToken) authentication;

		String password = (String) authenticationToken.getCredentials();
		String subAccount = (String) authenticationToken.getPrincipal();
		String mainAccount = authenticationToken.getAccountId();

		AppUserDetails userDetails =
			((AuthorizationServicesUserDetails) userDetailsService).loadUserBySbuAccount(mainAccount,subAccount);

		if (userDetails == null){
			throw new UsernameNotFoundException(I18nUtil.getMessage(CommonConstant.ACCOUNT_OR_PASSWORD_ERROR));
		}

		validatePassword(RSANewUtil.decrypt(password),userDetails,authenticationToken);

		SubAccountAuthenticationToken result =
			new SubAccountAuthenticationToken(userDetails,authentication.getCredentials(),new HashSet<>() );
		result.setDetails(authentication.getDetails());
		return result;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return SubAccountAuthenticationToken.class.isAssignableFrom(authentication);
	}

	/**
	 * 密码校验
	 * @param rawPassword
	 * @param userDetails
	 */
	private void validatePassword(String rawPassword, AppUserDetails userDetails,SubAccountAuthenticationToken authentication){
		try {
			String presentedPassword = RSANewUtil.decrypt(rawPassword);
			if (!new BCryptPasswordEncoder().matches(presentedPassword, userDetails.getPassword())) {
				throw new PasswordErrorException(
					accountLoginUtils.getFailureMessage(userDetails.getTenantId(),userDetails.getUsername(),userDetails.getAreaCode()));
			}
		} catch (Exception e) {
			authentication.setDetails(userDetails);
			throw new PasswordErrorException(
				accountLoginUtils.getFailureMessage(userDetails.getTenantId(),userDetails.getAccount(),userDetails.getAreaCode()));
		}
	}
}
