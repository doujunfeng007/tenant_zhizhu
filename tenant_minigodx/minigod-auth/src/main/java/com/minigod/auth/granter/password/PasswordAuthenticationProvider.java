package com.minigod.auth.granter.password;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.minigod.auth.constant.AuthConstant;
import com.minigod.auth.exception.PasswordErrorException;
import com.minigod.auth.exception.PasswordErrorThanMaxException;
import com.minigod.auth.exception.PasswordMissingException;
import com.minigod.auth.service.AppUserDetails;
import com.minigod.auth.service.MinigodUserDetailsService;
import com.minigod.auth.utils.AccountLoginUtils;
import com.minigod.auth.utils.RSANewUtil;
import com.minigod.zero.core.launch.constant.TokenConstant;
import com.minigod.zero.core.tool.utils.WebUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

import java.util.LinkedHashMap;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/5/10 21:44
 * @description：
 */
public class PasswordAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
	/**
	 * The plaintext password used to perform PasswordEncoder#matches(CharSequence,
	 * String)} on when the user is not found to avoid SEC-2056.
	 */
	private static final String USER_NOT_FOUND_PASSWORD = "userNotFoundPassword";


	private AccountLoginUtils accountLoginUtils;

	private PasswordEncoder passwordEncoder;

	/**
	 * The password used to perform {@link PasswordEncoder#matches(CharSequence, String)}
	 * on when the user is not found to avoid SEC-2056. This is necessary, because some
	 * {@link PasswordEncoder} implementations will short circuit if the password is not
	 * in a valid format.
	 */
	private volatile String userNotFoundEncodedPassword;

	private MinigodUserDetailsService userDetailsService;

	private UserDetailsPasswordService userDetailsPasswordService;

	public PasswordAuthenticationProvider() {
		setPasswordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
	}

	@Override
	@SuppressWarnings("deprecation")
	protected void additionalAuthenticationChecks(UserDetails userDetails,
												  UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		if (authentication.getCredentials() == null) {
			this.logger.debug("Failed to authenticate since no credentials provided");
			throw new BadCredentialsException(this.messages
				.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
		}
		if (StringUtils.isEmpty(userDetails.getPassword())){
			throw new PasswordMissingException("missing password");
		}
		AppUserDetails appUserDetails = (AppUserDetails) userDetails;

		try {
			String presentedPassword = RSANewUtil.decrypt(authentication.getCredentials().toString());
			if (!this.passwordEncoder.matches(presentedPassword, userDetails.getPassword())) {
				this.logger.debug("Failed to authenticate since password does not match stored value");
				throw new PasswordErrorException(
					accountLoginUtils.getFailureMessage(appUserDetails.getTenantId(),appUserDetails.getUsername(),appUserDetails.getAreaCode()));
			}
		} catch (Exception e) {
			authentication.setDetails(appUserDetails);
			throw new PasswordErrorException(
				accountLoginUtils.getFailureMessage(appUserDetails.getTenantId(),appUserDetails.getUsername(),appUserDetails.getAreaCode()));
		}
	}

	@Override
	protected void doAfterPropertiesSet() {
		Assert.notNull(this.userDetailsService, "A UserDetailsService must be set");
	}

	private void passwordErrorNumCheck(String tenantId,String phone,String areaCode){
		if (accountLoginUtils.lockAccount(tenantId,phone,areaCode)){
			throw new PasswordErrorThanMaxException(accountLoginUtils.getFailureMessage(tenantId,phone,areaCode));
		}
	}

	@Override
	protected final UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
		throws AuthenticationException {
		prepareTimingAttackProtection();
		try {
			LinkedHashMap detail = (LinkedHashMap) authentication.getDetails();
			String areaCode = (String) detail.get("areaCode");
			//如果是理财账号，areaCode给一个默认值，以兼容密码输入错误控制
			String  loginType = WebUtil.getRequest().getHeader(AuthConstant.LOGIN_TYPE);
			if (AuthConstant.LOGIN_SOURCE.equalsIgnoreCase(loginType)){
				areaCode = AuthConstant.LOGIN_SOURCE;
			}
//			String tenantId = WebUtil.getRequest().getHeader(TokenConstant.TENANT_KEY);
			String tenantId =TokenConstant.DEFAULT_TENANT_ID;
			if (StringUtils.isEmpty(tenantId)){
				throw new InternalAuthenticationServiceException("tenantId is null");
			}
			passwordErrorNumCheck(tenantId,username,areaCode);
			AppUserDetails loadedUser = (AppUserDetails) this.getUserDetailsService().loadUserByUsername(username,areaCode,tenantId,"");
			if (loadedUser == null) {
				throw new InternalAuthenticationServiceException(
					"UserDetailsService returned null, which is an interface contract violation");
			}
			return loadedUser;
		}
		catch (UsernameNotFoundException ex) {
			mitigateAgainstTimingAttack(authentication);
			throw ex;
		}
		catch (InternalAuthenticationServiceException ex) {
			throw ex;
		}
		catch (Exception ex) {
			throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	protected Authentication createSuccessAuthentication(Object principal, Authentication authentication,
														 UserDetails user) {
		boolean upgradeEncoding = this.userDetailsPasswordService != null
			&& this.passwordEncoder.upgradeEncoding(user.getPassword());
		if (upgradeEncoding) {
			String presentedPassword = authentication.getCredentials().toString();
			String newPassword = this.passwordEncoder.encode(presentedPassword);
			user = this.userDetailsPasswordService.updatePassword(user, newPassword);
		}
		return super.createSuccessAuthentication(principal, authentication, user);
	}

	private void prepareTimingAttackProtection() {
		if (this.userNotFoundEncodedPassword == null) {
			this.userNotFoundEncodedPassword = this.passwordEncoder.encode(USER_NOT_FOUND_PASSWORD);
		}
	}

	private void mitigateAgainstTimingAttack(UsernamePasswordAuthenticationToken authentication) {
		if (authentication.getCredentials() != null) {
			String presentedPassword = authentication.getCredentials().toString();
			this.passwordEncoder.matches(presentedPassword, this.userNotFoundEncodedPassword);
		}
	}

	/**
	 * Sets the PasswordEncoder instance to be used to encode and validate passwords. If
	 * not set, the password will be compared using
	 * {@link PasswordEncoderFactories#createDelegatingPasswordEncoder()}
	 * @param passwordEncoder must be an instance of one of the {@code PasswordEncoder}
	 * types.
	 */
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		Assert.notNull(passwordEncoder, "passwordEncoder cannot be null");
		this.passwordEncoder = passwordEncoder;
		this.userNotFoundEncodedPassword = null;
	}

	protected PasswordEncoder getPasswordEncoder() {
		return this.passwordEncoder;
	}

	public void setUserDetailsService(MinigodUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	protected MinigodUserDetailsService getUserDetailsService() {
		return this.userDetailsService;
	}

	public void setUserDetailsPasswordService(UserDetailsPasswordService userDetailsPasswordService) {
		this.userDetailsPasswordService = userDetailsPasswordService;
	}

	public AccountLoginUtils getAccountLoginUtils() {
		return accountLoginUtils;
	}

	public void setAccountLoginUtils(AccountLoginUtils accountLoginUtils) {
		this.accountLoginUtils = accountLoginUtils;
	}
}
