package com.minigod.zero.customer.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/4/19 16:10
 * @description：
 */
@Data
public class StockSubAccountBalanceVO {
	/**
	 * 资金账号
	 */
	private String capitalAccount;
	/**
	 * 可取金额
	 */
	private BigDecimal availableAmount = BigDecimal.ZERO;

	/**
	 * 冻结金额
	 */
	private BigDecimal freezeAmount = BigDecimal.ZERO;

	/**
	 * 在途金额
	 */
	private BigDecimal transitedAmount = BigDecimal.ZERO;
}
