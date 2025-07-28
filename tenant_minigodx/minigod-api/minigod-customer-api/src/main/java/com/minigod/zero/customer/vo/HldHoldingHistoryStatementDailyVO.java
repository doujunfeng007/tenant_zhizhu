package com.minigod.zero.customer.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName: com.minigod.zero.customer.vo.hldHoldingStatementMonthVO
 * @Description: 活利得日结单-2-2.活利得持仓总览
 * @Author: linggr
 * @CreateDate: 2024/5/24 0:52
 * @Version: 1.0
 */
@Data
public class HldHoldingHistoryStatementDailyVO {
	private String id;

	/**
	 * 证券名称
	 */
	private String hldName;
	/**
	 * productId
	 */
	private String productId;
	/**
	 * 证券代码
	 */
	private String hldCode;

	/**
	 * 币种
	 */
	private String currency;
	/**
	 * 买入份额
	 */
	private BigDecimal quantity;
	/**
	 * 单位面值
	 */
	private String nominalValue;

	/**
	 * 票面金额
	 */
	private BigDecimal faceAmount;
	/**
	 * 市价
	 */
	private BigDecimal price;

	/**
	 * 累计利息
	 */
	private BigDecimal totalGainLoss;

	/**
	 * 兑换汇率
	 */
	private BigDecimal rate;

	/**
	 * 市值
	 */
	private BigDecimal hldMarket;
	private Date createTime;

}
