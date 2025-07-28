package com.minigod.zero.platform.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/4/19 14:19
 * @description：子账号余额
 */
@Data
public class AccountBalanceVO implements Serializable {
	private String subAccount;
	/**
	 * 币种，USD HKD CNY
	 */
	private String currency;
	/**
	 *可用余额
	 */
	private BigDecimal availableBalance;
	/**
	 * 冻结金额
	 */
	private BigDecimal frozenBalance;
	/**
	 * 在途金额
	 */
	private BigDecimal transitedBalance;
}
