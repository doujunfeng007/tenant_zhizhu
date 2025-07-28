package com.minigod.zero.customer.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/5/7 10:42
 * @description：客户持仓
 */
@Data
public class CustomerFundHoldingVO {

	/**
	 * 基金名称
	 */
	private String fundName;
	/**
	 * 基金代码
	 */
	private String fundCode;
	/**
	 * 币种
	 */
	private String currency;
	/**
	 * 基金净值
	 */
	private BigDecimal price;
	/**
	 * 份额
	 */
	private BigDecimal holdingNumber;
	/**
	 * 市值
	 */
	private BigDecimal marketValue;
}
