package com.minigod.zero.bpmn.module.edda.bo;

import lombok.Data;

@Data
public class FundDepositAuthEddaBO {

	private String bankIdNo;

	/**
	 * {@link com.minigod.zero.biz.common.enums.IdKindType}
	 */
	private Integer bankIdKind;

	/**
	 * {@link com.minigod.zero.biz.common.enums.BankAccountType}
	 */
	private Integer depositAccountType;

	/**
	 * 银行账户账号不能为空
	 */
	private String depositAccount;
	/**
	 * 银行配置id
	 */
	private Long secDepositBankId;
	/**
	 * 分行号
	 */
	private String bankIdQuick;

	/**
	 * {@link com.minigod.zero.biz.common.enums.BankType}
	 */
	private Integer bankType;

	/**
	 * 币种
	 */
	private String currency;

	/**
	 * 客户选择的银行机构号
	 */
	private String bankId;
}
