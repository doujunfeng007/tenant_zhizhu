package com.minigod.zero.trade.vo.sjmb.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @author chen
 * @ClassName FundDepositReq.java
 * @Description TODO
 * @createTime 2024年02月29日 18:17:00
 */
@Data
public class FundDepositReq implements Serializable {

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

}
