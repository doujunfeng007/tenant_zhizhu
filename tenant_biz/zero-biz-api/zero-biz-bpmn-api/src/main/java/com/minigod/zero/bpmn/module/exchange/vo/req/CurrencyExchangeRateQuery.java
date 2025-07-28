package com.minigod.zero.bpmn.module.exchange.vo.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @author chen
 * @ClassName CurrencyExchangeRateQuery.java
 * @Description TODO
 * @createTime 2024年03月16日 16:33:00
 */
@Data
public class CurrencyExchangeRateQuery implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 目标币种[0-人民币CNY 1-美元USD 2-港币HKD]
	 */
	private Integer buyCcy;

	/**
	 * 原始币种[0-人民币CNY 1-美元USD 2-港币HKD]
	 */
	private Integer sellCcy;

	/**
	 * 汇率方向
	 */
	private Integer exchangeDirection;

	/**
	 * 状态
	 */
	private Integer status;

	/**
	 * 汇率日期 yyyy-MM-dd
	 */
	private String initDate;

	/**
	 * 租户id
	 */
	private String tenantId;
}
