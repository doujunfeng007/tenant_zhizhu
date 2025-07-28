package com.minigod.zero.trade.afe.req;

import lombok.Data;

/**
 * @ClassName: com.minigod.zero.trade.afe.req.DepositAmountReq
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/4/12 13:51
 * @Version: 1.0
 */
@Data
public class DepositAmountReq extends ReqVO{

	/**
	 * 对应 <ACCOUNTID>
	 */
	private String accountId;

	/**
	 * 币种
	 */
	private String currencyId;

	/**
	 * 结算接口id
	 */
	private String settleInterfaceId;

	/**
	 * 金额
	 */
	private String amount;

	/**
	 * 交易参考号
	 */
	private String transactionReference;

	/**
	 * 	内部备注
	 */
	private String internalRemark;

	/**
	 * 	对账文件
	 */
	private String receiptFile;

	/**
	 * 	对账文件名
	 */
	private String receiptFileName;

	/**
	 * 语言
	 */
	private String languageId;
}
