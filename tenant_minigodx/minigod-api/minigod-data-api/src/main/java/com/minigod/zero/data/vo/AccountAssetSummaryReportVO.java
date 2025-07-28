package com.minigod.zero.data.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/7/30 17:35
 * @description：
 */
@Data
public class AccountAssetSummaryReportVO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	private Integer id;

	/**
	 * 理财账号
	 */
	private String accountId;

	/**
	 * 客户姓名
	 */
	private String customerName;

	/**
	 * 归档日期
	 */
	private String statisticalTime;

	/**
	 * 账户类型
	 */
	private String accountType;

	/**
	 * 币种
	 */
	private String currency;

	/**
	 * 上日结余
	 */
	private BigDecimal lastDayBalance;

	/**
	 * 当日交收
	 */
	private BigDecimal sameDaySettlement;

	/**
	 * 当日结余
	 */
	private BigDecimal todayBalance;

	/**
	 * 当日市值
	 */
	private BigDecimal sameDayMarketValue;

	/**
	 * 当日存入
	 */
	private BigDecimal depositBalance;

	/**
	 * 当日提取
	 */
	private BigDecimal withdrawalBalance;

	/**
	 * 出入金净值
	 */
	private BigDecimal settlement;

	/**
	 * 交易净款项
	 */
	private BigDecimal netTransactionValue;

	/**
	 * 净结余
	 */
	private BigDecimal netSurplus;
}
