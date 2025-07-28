package com.minigod.zero.bpmn.module.deposit.bo;

import lombok.Data;

@Data
public class DepositBankInfoBo {
	/**
	 * 1-香港本地银行 2-中国大陆银行 3-海外银行
	 */
	private Integer bankType;
	/**
	 * 0-人民币 1-美元 2-港币
	 */
	private Integer moneyType;
	/**
	 * 银行卡key
	 */
	private String key;
	/**
	 * 接受转账方式1fps 2网银 3支票 4快捷入金 5银证转账 6edda
	 */
	private String supportType;
	/**
	 * 入金银行id
	 */
	private Long payeeBankDetailId;
}
