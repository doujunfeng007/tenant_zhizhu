package com.minigod.zero.customer.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/4/19 15:56
 * @description：
 */
@Data
public class StockAccountBalanceVO {
	/**
	 * 可取金额
	 */
	private BigDecimal availableAmount = BigDecimal.ZERO;
	/**
	 * 冻结金额
	 */
	private BigDecimal freezeAmount = BigDecimal.ZERO;
	/**
	 * 币种
	 */
	private String currency;
	/**
	 * 在途金额
	 */
	private BigDecimal transitedAmount = BigDecimal.ZERO;
	/**
	 * 子账号余额
	 */
	private List<StockSubAccountBalanceVO> stockSubAccountBalanceList;
}
