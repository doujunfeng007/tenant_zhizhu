package com.minigod.zero.customer.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/6/20 20:08
 * @description：
 */
@Data
public class DividendDistributionRecords {
	private Integer Id;
	/**
	 * 派息时间
	 */
	private String dividendTime;
	/**
	 * 派息状态
	 */
	private String status;
	/**
	 * 产品名称
	 */
	private String productName;
	/**
	 * 产品编号
	 */
	private String productCode;
	/**
	 * 币种
	 */
	private String currency;
	/**
	 * 派息金额
	 */
	private BigDecimal amount;
}
