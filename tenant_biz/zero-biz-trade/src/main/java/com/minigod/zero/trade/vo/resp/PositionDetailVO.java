package com.minigod.zero.trade.vo.resp;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author:yanghu.luo
 * @create: 2023-05-16 16:41
 * @Description: 单个持仓明细
 */
@Data
public class PositionDetailVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 证券代码
	 */
	private String stockCode;
	/**
	 * 证券名称
	 */
	private String stockName;
	/**
	 * 持仓市值=最新价*当前数量
	 */
	private String marketValue;
	/**
	 * 今日盈亏 = (今日市值 - 昨日市值) + (今日卖出成交额 - 今日买入成交额)
	 */
	private String todayIncome;
	/**
	 * 持仓盈亏 = (现价-成本) x 持仓数量
	 */
	private String incomeBalance;
	/**
	 * 持仓盈亏率 = (现价-成本) / 成本价 = 持仓盈亏 / 持有期内 (累计买入金额 - 累计卖出金额)
	 * 当成本价为负数时，盈亏率计算结果无意义，因此展示为0.00%
	 */
	private String incomeRatio;
	/**
	 * 最新价
	 */
	private String lastPrice;
	/**
	 * 买入均价
	 */
	private String avBuyPrice;
	/**
	 * 成本，取保本价
	 */
	private String costPrice;
	/**
	 * 当前数量
	 */
	private String currentAmount;
	/**
	 * 孖展比例
	 */
	private BigDecimal marginRatio;
	/**
	 * 可抵押额= 孖展比例 x 当前股票持仓市值
	 */
	private BigDecimal marginValue;
}
