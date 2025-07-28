/*
 * Copyright (c)  2022. ZSDP Inc. All rights reserved.
 */
package com.minigod.auth.service;

import com.minigod.zero.core.tool.support.Kv;
import com.minigod.zero.customer.vo.CustomerInfoVO;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 用户信息拓展
 *
 * @author guangjie.Liao
 */
@Data
public class AppUserDetails implements UserDetails {
	/**
	 * 用户id
	 */
	private  Long userId;
	/**
	 * 租户ID
	 */
	private  String tenantId;
	/**
	 * 昵称
	 */
	private  String name;
	/**
	 * 真名
	 */
	private  String realName;
	/**
	 * 账号
	 */
	private  String user_name;
	/**
	 * 状态
	 */
	private  Integer status;
	/**
	 * 用户详情
	 */
	private final Kv detail;
	/**
	 * 密码
	 */
	private transient String password;
	/**
	 * 理财账号
	 */
	private String account;
	/**
	 * 客户id
	 */
	private Long custId;
	/**
	 * 开户邮箱
	 */
	private String email;
	/**
	 * 区号
	 */
	private String areaCode;

	/**
	 * 手机号码
	 */
	private String phoneNumber;
	/**
	 * 是否是白名单用户
	 */
	private Boolean isWhiteList;
	/**
	 * 角色id
	 */
	private Integer roleId;


	public AppUserDetails(CustomerInfoVO entity) {
		this.userId = entity.getId();
		this.tenantId = entity.getTenantId();
		this.name = entity.getNickName();
		this.realName = entity.getRealName();
		this.user_name = entity.getCellPhone();
		this.phoneNumber= entity.getCellPhone();
		this.areaCode = entity.getAreaCode();
		this.detail = null;
		this.status = entity.getStatus();
		this.password = entity.getPassword();
		this.account = entity.getAccount();
		this.custId = entity.getId();
		this.email = entity.getEmail();
		this.isWhiteList = entity.getIsWhiteList();
		this.roleId = entity.getRoleId();
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new ArrayList<>();
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.user_name;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
