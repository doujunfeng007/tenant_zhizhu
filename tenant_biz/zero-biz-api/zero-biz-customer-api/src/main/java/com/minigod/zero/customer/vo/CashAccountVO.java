package com.minigod.zero.customer.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/6/20 13:28
 * @description：
 */
@Data
public class CashAccountVO {
	/**
	 * 币种
	 */
	private String currency;
	/**
	 * 可用金额
	 */
	private BigDecimal availableAmount;
	/**
	 * 可取
	 */
	private BigDecimal  withdrawalAmount;
	/**
	 * 冻结金额
	 */
	private BigDecimal freezeAmount;
	/**
	 * 在途金额
	 */
	private BigDecimal transitedAmount;
}
