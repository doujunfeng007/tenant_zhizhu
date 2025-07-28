package com.minigod.zero.trade.vo.req;

import lombok.Data;

@Data
public class CombinedAvailableBalanceVO {

	/**
	 * 目标币种
	 */
	private String toMoneyType;

	/**
	 * 资金账号
	 */
	private String fundAccount;

	/**
	 * 客户号
	 */
	private Long userId;

}
