package com.minigod.zero.trade.vo.sjmb.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @author chen
 * @ClassName FundUnFreezeReq.java
 * @Description TODO
 * @createTime 2024年03月01日 17:46:00
 */
@Data
public class FundUnFreezeReq implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 冻结资金的id
	 */
	private String lockedCashId;

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


}
