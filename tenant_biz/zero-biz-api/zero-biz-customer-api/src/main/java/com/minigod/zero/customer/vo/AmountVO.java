package com.minigod.zero.customer.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/5/11 13:08
 * @description：
 */
@Data
public class AmountVO {
	private String subAccount;
	private BigDecimal amount;
	private String currency;
	/**
	 * 0：扣除冻结，1：冻结转可用
	 */
	private Integer thawingType;
}
