package com.minigod.zero.cust.cache;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 登录客户信息
 */
@Data
public class CustInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 客户ID
	 */
	private Long custId;

	/**
	 * 昵称
	 */
	private String nickName;

	/**
	 * 头像
	 */
	private String custIcon;

	/**
	 * 区号
	 */
	private String areaCode;

	/**
	 * 手机号
	 */
	private String cellPhone;

	/**
	 * 电子邮箱
	 */
	private String cellEmail;

	/**
	 * 登录密码
	 */
	private String password;

	/**
	 * 微信开发平台授权ID
	 */
	private String wechatId;

	/**
	 * 账号列表
	 */
	private List<AcctInfo> acctList;

	/**
	 * 是否异地登录：0-是 1-否
	 */
	private Integer remoteLogin;

	/**
	 * 是否需要修改密码：0-需要 1-不需要
	 */
	private Integer pwdStatus;

	/**
	 * 客户类型：0-游客 1-普通个户 2-认证投顾 3-官方账号 4-公司授权人 5-ESOP客户
	 */
	private Integer custType;

	/**
	 * 绑定客户ID
	 */
	private Long bindCust;

	/**
	 * 客户状态：0-停用 1-正常 2-锁定 3-注销 4-使用开户手机号登录等待确认交易密码
	 */
	private Integer status;

	/**
	 * 0：非ESOP限制户
	 * 1：ESOP限制户，且未绑定个人户（不可切换，可以绑定）
	 * 2：ESOP限制户，且已绑定个人户（可切换，不可绑定）
	 */
	private Integer esopStatus;

}
