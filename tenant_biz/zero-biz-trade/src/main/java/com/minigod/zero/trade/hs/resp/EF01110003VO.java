package com.minigod.zero.trade.hs.resp;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by sunline on 2016/5/7 16:08.
 * sunline
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EF01110003VO implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 币种类别 0-人民币 1-美元 2-港币 3-其他
	 */
	@JSONField(name="money_type")
	private String moneyType;

	/**
	 * 单市场账面结余(账面结余 = 可取 + 在途 + 冻结)(单市场现金)
	 */
	@JSONField(name="tradeday_balance")
	private String tradedayBalance;

	/**
	 * 单市场冻结金额(加上交易冻结金额才等于总冻结金额)
	 */
	@JSONField(name="frozen_balance")
	private String frozenBalance;

	/**
	 * 单市场交易冻结金额
	 */
	@JSONField(name="cash_on_hold")
	private String cashOnHold;

	/**
	 * 单市场市值
	 */
	@JSONField(name="market_value")
	private String marketValue;

	/**
	 * 单市场当日买入金额
	 */
	@JSONField(name="real_buy_balance")
	private BigDecimal realBuyBalance;

	/**
	 * 单市场当日卖出金额
	 */
	@JSONField(name="real_sell_balance", defaultValue = "0")
	private BigDecimal realSellBalance;

	/**
	 * 单市场T日可取金额
	 */
	@JSONField(name="gf_fetch_balance_t", defaultValue = "0")
	private String fetchBalance;

	/**
	 * 单市场可用金额（购买力）
	 */
	@JSONField(name="enable_balance")
	private String enableBalance;

	/**
	 * 抵押额度/信用额度)
	 */
	@JSONField(name="max_exposure")
	private String maxExposure;

	/**
	 * 未交收买入金额
	 */
	@JSONField(name="uncome_buy_balance", defaultValue = "0")
	private BigDecimal uncomeBuyBalance;

	/**
	 * 未交收卖出金额
	 */
	@JSONField(name="uncome_sell_balance", defaultValue = "0")
	private BigDecimal uncomeSellBalance;

	/**
	 * 待交收金额：未交收卖出金额 + 当日卖出金额- 未交收买入金额 - 当日买入金额
	 */
	private BigDecimal unsettBalance;

	/**
	 * 交易市场 0-A股 1-美股 2-港股 3-其他
	 */
	private String marketType;

	/**
	 * 单市场今日盈亏(今日市值 + 今日卖出成交额 - 昨日市值 - 今日买入成交额)
	 */
	private String todayIncome;

	/**
	 * 单市场今日盈亏率 = 单市场今日盈亏 /（Σ 个股昨日持仓市值 + Σ 个股今日买入成交额）
	 */
	private String todayIncomeRatio;

	/**
	 * 单市场持仓亏率(Σ个股[(现价-成本) x 持仓数量])
	 */
	private String incomeBalance;

	/**
	 * 单市场持仓盈亏率(单市场持仓亏率/单市场持仓成本)
	 */
	private String incomeBalanceRatio;

	/**
	 * 个股持仓明细
	 */
	private List<EF01110005VO> positions;

}
