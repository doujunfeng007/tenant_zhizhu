package com.minigod.auth.granter.subaccount;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.util.Collection;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/12/2 13:32
 * @description：
 */
public class SubAccountAuthenticationToken extends AbstractAuthenticationToken {

	private Object principal;
	private Object credentials;
	private String accountId;

	/**
	 * Creates a token with the supplied array of authorities.
	 *
	 * @param authorities the collection of <tt>GrantedAuthority</tt>s for the principal
	 *                    represented by this authentication object.
	 */
	public SubAccountAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
	}

	/**
	 * 未授权
	 *
	 * @param principal
	 * @param credentials
	 * @param accountId
	 */
	public SubAccountAuthenticationToken(Object principal, Object credentials, String accountId) {
		super(null);
		this.principal = principal;
		this.credentials = credentials;
		this.accountId = accountId;
		super.setAuthenticated(false);
	}

	/**
	 * 已授权
	 *
	 * @param principal
	 * @param credentials
	 * @param authorities
	 */
	public SubAccountAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.principal = principal;
		this.credentials = credentials;
		super.setAuthenticated(true);
	}

	@Override
	public Object getCredentials() {
		return this.credentials;
	}

	@Override
	public Object getPrincipal() {
		return this.principal;
	}

	public String getAccountId() {
		return this.accountId;
	}
	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		Assert.isTrue(!isAuthenticated, "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
		super.setAuthenticated(false);
	}
	@Override
	public void eraseCredentials() {
		super.eraseCredentials();
		this.credentials = null;
	}

}
