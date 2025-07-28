package com.minigod.auth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/6/1 15:26
 * @description：
 */
public interface AuthorizationUserDetailsService extends UserDetailsService {
	/**
	 * 手机号，理财账号登录
	 * @param username
	 * @param areaCode
	 * @param tenantId
	 * @param param
	 * @return
	 * @throws UsernameNotFoundException
	 */
	UserDetails loadUserByUsername(String username, String areaCode, String tenantId, String param) throws UsernameNotFoundException;

	/**
	 * 子账号登录
	 * @param accountId
	 * @param subAccount
	 * @return
	 */
	UserDetails loadUserBySbuAccount(String accountId,String subAccount);
}
