package com.minigod.zero.trade.sjmb.req.brokerapi;

import lombok.Data;

import java.io.Serializable;

/**
 * @author chen
 * @ClassName WithdrawalReq.java
 * @Description TODO
 * @createTime 2024年03月01日 16:34:00
 */
@Data
public class WithdrawalReq implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 资金账号
	 */
	private String accountId;
	/**
	 * 金额
	 */
	private long amount;
	/**
	 * 币种
	 */
	private String currency;
	/**
	 * 转入银行账号
	 */
	private String inwardBankAccId;
	/**
	 * 转出银行账号
	 */
	private String outwardBankAccId;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 资金组
	 */
	private String segmentId;
	/**
	 * 流水号
	 */
	private String transactionId;
}
