package com.minigod.zero.customer.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/7/17 10:56
 * @description：
 */
@Data
public class CustomerVO {
	private Long custId;
	private String accountId;
	private String custName;
	private BigDecimal availableAmountHKD = BigDecimal.ZERO;
	private BigDecimal availableAmountUSD = BigDecimal.ZERO;
	private BigDecimal availableAmountCNY = BigDecimal.ZERO;
}
