package com.minigod.zero.customer.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/7/30 17:35
 * @description：
 */
@Data
public class CustomerCashAssetsVO {
	private String id;
	private String statisticalTime;
	private String accountId;
	private String customerName;
	private String accountType;
	private String currency;
	private BigDecimal settlement;
	private BigDecimal todayBalance;
	private BigDecimal lastDayBalance;
}
