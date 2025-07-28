
package com.minigod.zero.biz.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minigod.zero.cust.cache.AcctInfo;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

/**
 * 用户信息拓展
 *
 * @author Chill
 */
@Getter
public class ZeroUserDetails extends User {

	/**
	 * 租户ID
	 */
	private String tenantId;
	/**
	 * 微信开发平台授权ID
	 */
	private final String wechatId;
	/**
	 * 昵称
	 */
	private final String nickName;
	/**
	 * 头像
	 */
	private final String avatar;
	/**
	 * 用户邮箱
	 */
	private final String custEmail;

	/**
	 * 设备号
	 */
	private final String deviceCode;

	/**
	 * 客户号
	 */
	private final String custId;

	/**
	 * 客户类型：1-个人 4-公司授权人
	 */
	private final Integer custType;

	/**
	 * 客户状态：0-停用 1-正常 2-锁定 3-注销 4-使用开户手机号登录等待确认交易密码
	 */
	private final Integer status;

	/**
	 * 0：非ESOP限制户
	 * 1：ESOP限制户，且未绑定个人户（不可切换，可以绑定）
	 * 2：ESOP限制户，且已绑定个人户（可切换，不可绑定）
	 */
	private final Integer esopStatus;

	/**
	 * 客户手机号
	 */
	private final String custPhone;
	/**
	 * 登录终端
	 */
	private final String terminal;

	/**
	 * 账号列表
	 */
	@JsonProperty
	private final List<AcctInfo> acctList;

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public ZeroUserDetails(String custId, String wechatId, String nickName, String avatar, String password, Integer custType,
						   String deviceCode, String custPhone, String custEmail, List<AcctInfo> acctList, String terminal, Integer status,Integer esopStatus,
						   boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
		super(custId, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.custId = custId;
		this.wechatId = wechatId;
		this.nickName = nickName;
		this.avatar = avatar;
		this.deviceCode = deviceCode;
		this.custType = custType;
		this.custPhone = custPhone;
		this.custEmail = custEmail;
		this.acctList = acctList;
		this.terminal = terminal;
		this.status = status;
		this.esopStatus = esopStatus;
	}

}
