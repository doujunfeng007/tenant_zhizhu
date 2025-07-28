package com.minigod.zero.customer.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/4/19 16:08
 * @description：
 */
@Data
public class FundSubAccountBalanceVO {
	private Long id;
	/**
	 * 基金账户
	 */
	private String fundAccount;
	/**
	 * 在途金额
	 */
	private BigDecimal transitedAmount= BigDecimal.ZERO;
	/**
	 * 冻结金额
	 */
	private BigDecimal freezeAmount= BigDecimal.ZERO;
	/**
	 * 可用金额
	 */
	private BigDecimal availableAmount= BigDecimal.ZERO;
}
