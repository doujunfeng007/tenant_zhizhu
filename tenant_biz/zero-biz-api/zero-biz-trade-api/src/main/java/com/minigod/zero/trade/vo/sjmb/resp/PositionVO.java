package com.minigod.zero.trade.vo.sjmb.resp;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author chen
 * @ClassName PositionVO.java
 * @Description TODO
 * @createTime 2024年01月24日 15:45:00
 */
@Data
public class PositionVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String exchangeType;

	private String stockCode;

	private String assetId;

	/**
	 * 每手股数
	 */
	private Integer  lotSize;

	private Integer secSType;

	private String stockName;

	private String stockNameZhCN;

	private String stockNameZhHK;

	/**
	 * 买入均价
	 */
	private BigDecimal avBuyPrice = new BigDecimal("0");

	/**
	 * 成本价
	 */
	private BigDecimal costPrice = new BigDecimal("0");

	/**
	 * 当前数量
	 */
	private BigDecimal currentQty = new BigDecimal("0");

	/**
	 * 可用数量
	 */
	private BigDecimal enableQty = new BigDecimal("0");

	/**
	 * 最新价
	 */
	private BigDecimal lastPrice = new BigDecimal("0");

	/**
	 * 收盘价
	 */
	private BigDecimal closePrice = new BigDecimal("0");

	private String currency ;

	/**
	 * 市值
	 */
	private BigDecimal marketValue = new BigDecimal("0");

	/**
	 * 持仓盈亏
	 */
	private BigDecimal incomeBalance = new BigDecimal("0");

	/**
	 * 持仓盈亏比
	 */
	private BigDecimal incomeBalanceRatio = new BigDecimal("0");

	/**
	 * 今日盈亏
	 */
	private BigDecimal todayIncome =new BigDecimal("0");

	/**
	 * 今日盈亏比
	 */
	private BigDecimal todayIncomeRatio =new BigDecimal("0");

	/**
	 * STOCK  OPTION
	 */
	private String secType;
}
