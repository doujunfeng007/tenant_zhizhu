package com.minigod.zero.cust.cache;

import lombok.Data;

import java.io.Serializable;

/**
 * 客户账号信息
 */
@Data
public class AcctInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 交易账号
	 */
	private String tradeAccount;
	/**
	 * 账户类型：1-个人 2-联名 3-公司
	 */
	private Integer acctType;
	/**
	 * 是否当前选中
	 */
	private String isCurrent;
	/**
	 * 基金账户
	 */
	private String fundAccount;

	/**
	 * 保险账户
	 */
	private String insureAccount;

	/**
	 * 公司名称
	 */
	private String companyName;
	/**
	 * 公司名称英文
	 */
	private String companyNameEn;

	/**
	 * APP权限，格式：[a,b,c,d]
	 */
	private String appPermission;
}
