package com.minigod.zero.trade.afe.req;

import lombok.Data;

/**
 * @ClassName: com.minigod.zero.trade.afe.req.WithdrawAmountReq
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/4/12 14:50
 * @Version: 1.0
 */
@Data
public class WithdrawAmountReq extends ReqVO{
	private String accountId;
	private String currencyId;
	private String amount;
	private String transactionReference;
	private String internalRemark;
	private String languageId;
}
