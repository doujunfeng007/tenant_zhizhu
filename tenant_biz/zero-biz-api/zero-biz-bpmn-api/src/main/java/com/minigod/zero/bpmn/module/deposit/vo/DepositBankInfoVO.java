package com.minigod.zero.bpmn.module.deposit.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: sunline
 * @date: 2019/5/8 21:55
 * @version: v1.0
 */
@Data
public class DepositBankInfoVO implements Serializable{
    private static final long serialVersionUID = -357264065066759819L;

	/**
	 * 收款银行代码
	 */
	private String code;
	/**
	 * 收款账户号码
	 */
    private DepositBankToAccountVO depositToAccount;
	/**
	 * 银行名称
	 */
	private String bankName;
	/**
	 * 收款账户名称
	 */
    private String accountName;
	/**
	 * 收款账户名称备注
	 */
	private String accountNameRemark;
	/**
	 * swiftCode 代码
	 */
    private String swiftCode;
	/**
	 * 收款银行地址
	 */
	private String depositToBankAddress;
	/**
	 * 到账时间备注
	 */
    private String descTimeRemark;
	/**
	 * 收款人地址
	 */
	private String depositUserAddress;
	/**
	 * 收款银行英文名
	 */
    private String bankNameEN;
	/**
	 * 1:公司大账号 2:个人子账号
	 */
	private Integer accountType;

}
