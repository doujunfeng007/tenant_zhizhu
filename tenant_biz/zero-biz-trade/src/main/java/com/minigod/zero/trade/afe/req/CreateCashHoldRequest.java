package com.minigod.zero.trade.afe.req;

import lombok.Data;

/**
 * @author chen
 * @ClassName CreateCashHoldRequest.java
 * @Description 资金冻结
 * @createTime 2024年08月07日 10:53:00
 */
@Data
public class CreateCashHoldRequest extends ReqVO{

	private String valueDate;

	/**
	 * 交易参考号
	 */
	private String transactionReference;

	/**
	 * 资金账号
	 */
	private String accountId;

	/**
	 * 币种
	 */
	private String currencyId;

	/**
	 * 金额
	 */
	private String amount;

	/**
	 * 冻结类型
	 */
	private String holdType;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 自动审批标志  Y/N
	 */
	private String autoApprovalFlag;
}
