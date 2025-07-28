package com.minigod.auth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * UserDetailsService 接口扩展 支持多租户
 * 该接口主要用于spring security 认证, 认证成功后会返回一个UserDetails对象, 用于后续权限认证.
 */
public interface MinigodUserDetailsService extends UserDetailsService {
	UserDetails loadUserByUsername(String username, String areaCode, String tenantId, String param) throws UsernameNotFoundException;

	/**
	 * 子账号登录
	 *
	 * @param accountId
	 * @param subAccount
	 * @return
	 */
	UserDetails loadUserBySbuAccount(String accountId, String subAccount);

}
