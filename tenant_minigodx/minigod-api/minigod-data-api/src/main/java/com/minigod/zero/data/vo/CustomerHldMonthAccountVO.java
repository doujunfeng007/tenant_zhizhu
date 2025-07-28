package com.minigod.zero.data.vo;

import lombok.Data;
import org.apache.commons.collections.CollectionUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 活利得月结单返回实体类
 *
 * @param
 * @return
 */
@Data
public class CustomerHldMonthAccountVO {
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
	 * 活利得月结单交易明细集合
	 */
	private List<HldStatementMonthVO> hldTradeMonthVOs;

	/**
	 * 活利得月结单持仓总览集合
	 */
	private List<HldHoldingStatementMonthVO> hldHoldingMonthVOs;

	/**
	 * 市值总额 持仓vo hldMarket sum
	 */
	private BigDecimal sumMarketValue;

	/**
	 * 累积利息收益
	 */
	private BigDecimal totalGainLossSum;

	private String subAccountId;

	public BigDecimal getSumMarketValue() {
		if (CollectionUtils.isNotEmpty(hldHoldingMonthVOs) && hldHoldingMonthVOs.size()>0){
			sumMarketValue = hldHoldingMonthVOs.stream()
				.map(HldHoldingStatementMonthVO::getHldMarket) // 将每个VO的amount字段映射为BigDecimal类型
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		}else {
			sumMarketValue = BigDecimal.ZERO;
		}
		return  sumMarketValue;
	}

	public void setSumMarketValue(BigDecimal sumMarketValue) {
		if (CollectionUtils.isNotEmpty(hldHoldingMonthVOs) && hldHoldingMonthVOs.size()>0){
			this.sumMarketValue = hldHoldingMonthVOs.stream()
				.map(HldHoldingStatementMonthVO::getHldMarket) // 将每个VO的amount字段映射为BigDecimal类型
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		}else {
			this.sumMarketValue = BigDecimal.ZERO;
		}

	}


}
