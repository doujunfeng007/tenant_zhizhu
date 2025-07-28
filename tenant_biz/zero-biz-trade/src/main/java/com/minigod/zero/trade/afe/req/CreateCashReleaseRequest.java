package com.minigod.zero.trade.afe.req;

import lombok.Data;

/**
 * @author chen
 * @ClassName CreateCashReleaseRequest.java
 * @Description TODO
 * @createTime 2024年08月07日 14:18:00
 */
@Data
public class CreateCashReleaseRequest extends ReqVO{

	private String valueDate;

	/**
	 * 资金账号
	 */
	private String accountId;

	/**
	 * 币种
	 */
	private String currencyId;

	/**
	 * 交易参考号
	 */
	private String transactionReference;


	/**
	 * 金额
	 */
	private String amount;

	/**
	 *
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
