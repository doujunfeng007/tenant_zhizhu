package com.minigod.zero.trade.vo.sjmb.req;

import java.io.Serializable;

import lombok.Data;

/**
 * @author chen
 * @ClassName Withdrawal.java
 * @Description TODO
 * @createTime 2024年03月01日 16:19:00
 */
@Data
public class FundWithdrawalReq implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 客户号
	 */
	private String tradeAccount;

	/**
	 * 资金账号
	 */
	private String fundAccount;

	/**
	 * 币种  USD  HKD  CNY
	 */
	private String currency;

	/**
	 * 金额
	 */
	private String amount;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 本地备注
	 */
	private String localeRemark;

	/**
	 * 手续费
	 */
	private String fare;
}
