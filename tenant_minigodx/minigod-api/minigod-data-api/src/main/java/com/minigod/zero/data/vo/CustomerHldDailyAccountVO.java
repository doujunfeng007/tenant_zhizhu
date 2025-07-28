package com.minigod.zero.data.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 活利得日结单返回实体类
 *
 * @param
 * @return
 */
@Data
public class CustomerHldDailyAccountVO {
	/**
	 * 公司名称
	 */
	private String companyName;

	/**
	 * 公司logo地址
	 */
	private String companyLogoUrl;

	/**
	 * 结单日期
	 */
	private Date accountDate;

	/**
	 * 客户名称
	 */
	private String custName;

	/**
	 * 账户号码
	 */
	private String accountNumber;

	/**
	 * 账户币种
	 */
	private String accountCurrency;

	/**
	 * 联系地址
	 */
	private String accountContactAddress;


	/**
	 * 活利得日结单买入确认单集合
	 */
	private List<HldTradingBuyStatementDailyVO> hldBuyDailyVOs;

	/**
	 * 活利得日结单卖出确认单集合
	 */
	private List<HldTradingSaleStatementDailyVO> hldSaleDailyVOs;

	/**
	 * 活利得日结单持仓总览集合
	 */
	private List<HldHoldingHistoryStatementDailyVO> hldHoldingDailyVOs;

	/**
	 * 市值总额
	 */
	private BigDecimal sumMarketValue;

	/**
	 * 累积利息收益
	 */
	private BigDecimal totalGainLossSum;

	private String subAccountId;




}
