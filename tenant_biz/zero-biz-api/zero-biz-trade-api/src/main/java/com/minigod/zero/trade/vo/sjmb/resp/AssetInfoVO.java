package com.minigod.zero.trade.vo.sjmb.resp;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author chen
 * @ClassName AssetInfoVO.java
 * @Description TODO
 * @createTime 2024年01月19日 16:35:00
 */

@Data
public class AssetInfoVO implements Serializable {

	private static final long serialVersionUID = 1L;


	private String currency;

	/**
	 * 可用余额  购买力
	 */
	private BigDecimal enableBalance =new BigDecimal("0");

	/**
	 * 冻结资金
	 */
	private BigDecimal frozenBalance =new BigDecimal("0");

	/**
	 * 证券市值
	 */
	private BigDecimal marketValue =new BigDecimal("0");

	/**
	 * 总资产
	 */
	private BigDecimal asset =new BigDecimal("0");

	/**
	 * 现金可取
	 */
	private BigDecimal fetchBalance =new BigDecimal("0");

	/**
	 * 交易冻结金额
	 */
	private BigDecimal cashOnHold =new BigDecimal("0");

	/**
	 * 	现金
	 */
	private BigDecimal currentBalance =new BigDecimal("0");

	/**
	 * 风险级别
	 */
	private int riskLevel  ;

	/**
	 * 在途资金
	 */
	private BigDecimal totalBuyMoney =new BigDecimal("0");

	/**
	 * 信用额度
	 */
	private BigDecimal maxExposure =new BigDecimal("0");

	/**
	 * 今日盈亏
	 */
	private BigDecimal todayIncome =new BigDecimal("0");

	/**
	 * 今日盈亏比
	 */
	private BigDecimal todayIncomeRatio =new BigDecimal("0");

	/**
	 * 持仓盈亏
	 */
	private BigDecimal incomeBalance =new BigDecimal("0");

	/**
	 * 持仓盈亏比
	 */
	private BigDecimal incomeBalanceRatio =new BigDecimal("0");

	/**
	 * 剩余流动性
	 */
	private BigDecimal excessLiquidity =new BigDecimal("0");;

}
