package com.minigod.zero.customer.vo;

import lombok.Data;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/6/20 13:39
 * @description：
 */
@Data
public class CustomerBankCardVO {
	private Integer id;
	/**
	 * 银行类型
	 */
	private String bankTypeName;
	/**
	 * 银行名称
	 */
	private String bankName;
	/**
	 * 账户名称
	 */
	private String bankAccountName;
	/**
	 * 银行卡号
	 */
	private String bankCardNo;
	/**
	 * 银行编号
	 */
	private String bankCode;
	/**
	 * 是否入金
	 */
	private String isDeposit;
	/**
	 * 是否edda授权
	 */
	private String isEdda;
	/**
	 * 是否银证转账授权
	 */
	private String isTransferAccounts;
}
