package com.minigod.zero.data.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/10/29 10:49
 * @description：
 */
@Data
public class ProductAssetVO {
	/**
	 * 产品isin码
	 */
	private String productCode;
	/**
	 * 产品币种
	 */
	private String currency;
	/**
	 * 产品累计交易金额
	 */
	private BigDecimal accumulatedTransactionAmount = BigDecimal.ZERO;
	/**
	 * 累计购买人数
	 */
	private BigDecimal accumulateBuyers;
	/**
	 * 累计利息
	 */
	private BigDecimal accumulateGainLoss = BigDecimal.ZERO;
	/**
	 * 发行时间
	 */
	private Date issueDate;
	/**
	 * 发行天数
	 */
	private Long issuanceDays;
	/**
	 * 排序字段
	 */
	private BigDecimal sort;
}
