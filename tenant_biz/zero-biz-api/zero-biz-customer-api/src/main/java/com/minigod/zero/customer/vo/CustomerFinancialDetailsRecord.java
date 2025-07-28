package com.minigod.zero.customer.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/7/31 11:17
 * @description：
 */
@Data
public class CustomerFinancialDetailsRecord {
	private String id;
	private String statisticalTime;
	private String accountId;
	private String customerName;
	private String currency;
	private BigDecimal lastDayBalance;
	private BigDecimal depositBalance;
	private BigDecimal withdrawalBalance;
	private BigDecimal settlement;
}
