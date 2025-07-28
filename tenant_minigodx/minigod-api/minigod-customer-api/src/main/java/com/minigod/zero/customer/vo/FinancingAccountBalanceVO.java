package com.minigod.zero.customer.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/4/29 15:26
 * @description：理财账号金额信息
 */
@Data
public class FinancingAccountBalanceVO {

	private String accountId;
	/**
	 * 可取金额
	 */
	private BigDecimal availableAmount= BigDecimal.ZERO;
	/**
	 * 冻结金额
	 */
	private BigDecimal freezeAmount = BigDecimal.ZERO;
	/**
	 * 币种
	 */
	private String currency;
	/**
	 * 在途金额
	 */
	private BigDecimal transitedAmount= BigDecimal.ZERO;
}
