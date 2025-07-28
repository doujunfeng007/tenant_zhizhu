package com.minigod.zero.trade.vo.sjmb.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @author chen
 * @ClassName FundUnFreezeWithdrawReq.java
 * @Description TODO
 * @createTime 2024年03月19日 16:53:00
 */
@Data
public class FundUnFreezeWithdrawReq implements Serializable {

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
	 * 冻结金额
	 */
	private String amount;

	/**
	 * 冻结业务编号
	 */
	private String businessNumber;

	/**
	 * 币种  USD  HKD  CNY
	 */
	private String currency;

	/**
	 * 费用
	 */
	private String fare;

}
