package com.minigod.zero.customer.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/4/19 15:52
 * @description：
 */
@Data
public class CustomerAccountBalanceVO {
	/**
	 * 客户号（个人/授权人）
	 */
	private Long custId;
	/**
	 * 理财账户id
	 */
	private String accountId;
	/**
	 * 租户id
	 */
	private String tenantId;
	/**
	 * 总资产
	 */
	private BigDecimal totalAsset = BigDecimal.ZERO;
	/**
	 * 可用金额
	 */
	private BigDecimal availableAmount= BigDecimal.ZERO;
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
	private BigDecimal transitedAmount= BigDecimal.ZERO;
	/**
	 * 股票市值
	 */
	private BigDecimal stockMarketValue = BigDecimal.ZERO;
	/**
	 * 基金市值
	 */
	private BigDecimal fundMarketValue = BigDecimal.ZERO;
	/**
	 * 活利得市值
	 */
	private BigDecimal hldMarketValue = BigDecimal.ZERO;
	/**
	 * 债券易市值
	 */
	private BigDecimal bondMarketValue = BigDecimal.ZERO;

	/**
	 * 债券市值
	 */
	private BigDecimal otcMarketValue = BigDecimal.ZERO;

	/**
	 * 理财总市值
	 */
	private BigDecimal  positionMarketValue = BigDecimal.ZERO;

	/**
	 * 持仓净值
	 */
	private BigDecimal netPositionValue = BigDecimal.ZERO;

	/**
	 * 持仓收益
	 */
	private BigDecimal totalGainLoss = BigDecimal.ZERO;

	/**
	 * 总冻结金额
	 */
	private BigDecimal totalFreezeAmount = BigDecimal.ZERO;

	/**
	 * 总净值
	 */
	private BigDecimal  totalNetValue = BigDecimal.ZERO;

	/**
	 * 累计收益
	 */
	private BigDecimal accumulatedRevenue  = BigDecimal.ZERO;
}
