package com.minigod.zero.biz.common.mq;

import lombok.Data;

import java.io.Serializable;

/**
 * BPM将生效的公司关联人推送给CUST
 * @date 2023/6/3
 */
@Data
public class ConnecterInfo implements Serializable {

	/**
	 * 用户号
	 */
	private Long custId;

	/**
	 * 客户类型：1-普通个户 4-公司授权人 5-存量ESOP客户
	 */
	private Integer custType;
	/**
	 * 授权人ID
	 */
	private Long authorId;
	/**
	 * 是否授权登录APP 0:否，1:是
	 */
	private Integer loginAuth;
	/**
	 * 注册类型：1-手机，2-邮箱
	 */
	private Integer registerType;
	/**
	 * 授权人手机区号
	 */
	private String authorArea;
	/**
	 * 授权人手机号
	 */
	private String authorPhone;
	/**
	 * 登录密码
	 */
	private String loginPwd;
	/**
	 * 交易密码
	 */
	private String tradePwd;
	/**
	 * 交易账号
	 */
	private String tradeAccount;
	/**
	 * 交易授权：0-否 1-是
	 */
	private Integer tradeAuth;
	/**
	 * APP权限，格式：[a,b,c,d]
	 */
	private String appPermission;
	/**
	 * 授权人邮箱
	 */
	private String authorEmail;
	/**
	 * 账号状态
	 */
	private String acctStatus;
	/**
	 * 关联状态：3-已生效 4-已终止 5-已失效
	 */
	private Integer relationStatus;

	/**
	 * 资金账号
	 */
	private String fundAccount;

	/**
	 * 账户类型[0=现金账户 M=保证金账户]
	 */
	private String fundAccountType;

	/**
	 * 基金主账户
	 */
	private String fundAccountMain;

	/**
	 * 基金子账户
	 */
	private String yfFundAccount;

	/**
	 * 基金账户类型：0-个人户 1-机构户
	 */
	private String yfFundAccountType;

	/**
	 * 基金账户权限：0-不可申购和赎回 1-可申购可赎回 2-可申购不可赎回 3-不可申购可赎回
	 */
	private String yfFundOperType;

	/**
	 * 公司授权人证件号
	 */
	private String authorIdno;
}
