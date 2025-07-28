package com.minigod.zero.trade.vo.resp;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by sunline on 2016/5/27 15:31.
 * sunline
 */
@Data
public class StockHoldingVO implements Serializable {
    private static final long serialVersionUID = 1L;

	/**
	 * 交易类别(市场)
	 */
	@JSONField(name="exchange_type")
    private String exchangeType;
	/**
	 * 证券代码
	 */
	@JSONField(name="stock_code")
    private String stockCode;
	/**
	 * 证券名称
	 */
	@JSONField(name="stock_name")
    private String stockName;
	/**
	 * 股票简体名称
	 */
	@JSONField(name="stock_namegb")
	private String stockNameZhCN;
	/**
	 * 股票繁体名称
	 */
	@JSONField(name="stock_namebig5")
	private String stockNameZhHK;
	/**
	 * 当前数量
	 */
	@JSONField(name="current_amount")
    private String currentAmount;
	/**
	 * 可卖数量
	 */
	@JSONField(name="enable_amount")
    private String enableAmount;
	/**
	 * 回报卖出数量
	 */
	@JSONField(name="real_sell_amount")
	private String realSellAmount;
	/**
	 * 回报买入数量
	 */
	@JSONField(name="real_buy_amount")
	private String realBuyAmount;
	/**
	 * 最新价
	 */
	@JSONField(name="last_price")
	private String lastPrice;
	/**
	 * 昨收市价
	 */
	@JSONField(name="close_price")
	private BigDecimal closePrice;
	/**
	 * 成本，取保本价
	 */
	private String costPrice;
	/**
	 * 保本价
	 */
	@JSONField(name="keep_cost_price")
	private String keepCostPrice;
	/**
	 * 币种类别
	 */
	@JSONField(name="money_type")
    private String moneyType;
	/**
	 * 买入均价
	 */
	@JSONField(name="av_buy_price")
    private String avBuyPrice;
	/**
	 * 持仓市值=最新价*当前数量
	 */
	@JSONField(name="market_value")
	private String marketValue;
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
	 * 今日盈亏 = (今日市值 - 昨日市值) + (今日卖出成交额 - 今日买入成交额)
	 */
	private String todayIncome;
	/**
	 * 今日盈亏率 = 今日盈亏/(昨日市值 + 今日买入成交额) = 今日盈亏/((当前数量+回报卖出数量-回报买入数量)*昨收市价 + 今日买入成交额)
	 */
	private String todayIncomeRatio;
	/**
	 * 证券代码 带市场
	 */
	private String assetId;
	/**
	 * 昨日市值
	 */
	private String yesterdayMarketValue;
}
