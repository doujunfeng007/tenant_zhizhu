package com.minigod.zero.trade.vo.sjmb.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @author chen
 * @ClassName FundFreezeReq.java
 * @Description TODO
 * @createTime 2024年03月01日 17:26:00
 */

@Data
public class FundFreezeReq implements Serializable {

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
	 * 币种  USD  HKD  CNY
	 */
	private String currency;

	/**
	 * 业务日期
	 */
	private String businessDate;

	/**
	 * 备注
	 */
	private String remark;

	private String orderId;

}
