package com.minigod.zero.bpmn.module.deposit.vo;

import lombok.Data;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/7/15 14:48
 * @description：客户银行卡
 */
@Data
public class BankCardVO {
	private String bankId;
	private String bankName;
	private String bankCode;
	private String bankAccount;
	private String bankAccountName;
	private Long   custId;
}
