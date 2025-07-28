
package com.minigod.zero.auth.service;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * 用户信息拓展
 *
 * @author Chill
 */
@Getter
public class ZeroUserDetails extends User {

	/**
	 * 用户id
	 */
	private final Long userId;
	/**
	 * 租户ID
	 */
	private final String tenantId;
	/**
	 * 第三方认证ID
	 */
	private final String oauthId;
	/**
	 * 昵称
	 */
	private final String name;
	/**
	 * 真名
	 */
	private final String realName;
	/**
	 * 账号
	 */
	private final String account;
	/**
	 * 部门id
	 */
	private final String deptId;
	/**
	 * 岗位id
	 */
	private final String postId;
	/**
	 * 角色id
	 */
	private final String roleId;
	/**
	 * 角色名
	 */
	private final String roleName;
	/**
	 * 头像
	 */
	private final String avatar;
	/**
	 * 用户详情
	 */
	// private final Kv detail;

	/**
	 * 设备号
	 */
	private final String deviceCode;

	/**
	 * 客户号
	 */
	private final String custId;

	/**
	 * 客户手机号
	 */
	private final String custPhone;

	/**
	 * 0不需要修改手机号，1需要修改手机号
	 */
	private Integer updatePwd;


	public ZeroUserDetails(Long userId, String tenantId, String oauthId, String name, String realName, String deptId, String postId, String roleId, String roleName, String avatar,
						   String account, String username, String password, String deviceCode, String custId, String custPhone,
						   boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities,Integer updatePwd) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.userId = userId;
		this.tenantId = tenantId;
		this.oauthId = oauthId;
		this.name = name;
		this.realName = realName;
		this.account = account;
		this.deptId = deptId;
		this.postId = postId;
		this.roleId = roleId;
		this.roleName = roleName;
		this.avatar = avatar;
		this.deviceCode = deviceCode;
		this.custId = custId;
		this.custPhone = custPhone;
		this.updatePwd = updatePwd;
	}

}
