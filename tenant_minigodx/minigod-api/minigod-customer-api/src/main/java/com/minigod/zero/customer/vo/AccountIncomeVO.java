package com.minigod.zero.customer.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/5/6 21:28
 * @description：账户收益
 */
@Data
public class AccountIncomeVO {
	/**
	 * 总金额
	 */
	private BigDecimal totalAmount;
	/**
	 * 日收益
	 */
	private BigDecimal dailyEarnings;
	/**
	 * 持仓收益
	 */
	private BigDecimal holdingEarnings;
	/**
	 * 累计收益
	 */
	private BigDecimal totalEarnings;
}
