package com.minigod.zero.customer.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/6/20 17:35
 * @description：
 */
@Data
public class CustomerPositionsDetailVO {
	private Integer id;
	/**
     * 产品名称
	 */
	private String productName;
	/**
	 * 持仓市值
	 */
	private BigDecimal marketValue;
	/**
	 * 份额
	 */
	private BigDecimal availableShares;
	/**
	 * 冻结份额
	 */
	private BigDecimal frozenShares;
	/**
	 * 币种
	 */
	private String currency;
	/**
	 * 昨日收益
	 */
	private BigDecimal yesterdayIncome;
	/**
	 * 累计收益
	 */
	private BigDecimal totalIncome;

	/**
	 * 票面息率
	 */
	private BigDecimal couponRate;
	/**
	 * 预期年化收益
	 */
	private BigDecimal expectedAnnualYield;
}
