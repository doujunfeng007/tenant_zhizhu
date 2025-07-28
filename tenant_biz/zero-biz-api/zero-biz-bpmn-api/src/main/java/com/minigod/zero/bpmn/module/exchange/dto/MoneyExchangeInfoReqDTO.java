package com.minigod.zero.bpmn.module.exchange.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MoneyExchangeInfoReqDTO {

	/**
	 * 源货币
	 */
	private String fromMoneyType;

	/**
	 * 目标货币
	 */
	private String toMoneyType;

	/**
	 * 源货币数量
	 */
	private BigDecimal fromMoneyAmount;

	/**
	 * 目标货币数量
	 */
	private BigDecimal toMoneyAmount;

	/**
	 * 换汇方向 0-定额兑出，1-定额兑入
	 */
	private Integer moneyExchangeDirection;

	/**
	 * 汇率名称
	 */
	private String rateName;

	/**
	 * 用户号
	 */
	private Long userId;

	/**
	 * 行业 默认Brokerage
	 */
	private String IndustryType;

	/**
	 * 换汇类型 0-手工换汇，1-自动换汇
	 */
	private Integer moneyExchangeType;

	/**
	 * 状态 0-待审批，1-待兑换，2-失败,3-完成
	 */
	private Integer status;

	/**
	 * 客户中文名
	 */
	private String clientName;

	/**
	 * 交易账号
	 */
	private String tradeAccount;

	/**
	 * 资金账号
	 */
	private String fundAccount;

	/**
	 * 客户类型
	 * 0	Retail
	 * 1	Corporation
	 * 3	Staff
	 * 4	Institution
	 * 5	House
	 * a	test
	 * j	Joint
	 * m	Retail Client-PRC
	 * z	Corporate
	 */
	private String clientType;

	/**
	 * 邮箱
	 */
	private String email;
}
