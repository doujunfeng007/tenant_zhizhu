package com.minigod.zero.customer.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/5/11 13:08
 * @description：
 */
@Data
public class AmountDTO {
	private String subAccount;
	/**
	 * 金额
	 */
	private BigDecimal amount;
	/**
	 * 币种
	 */
	private String currency;
	/**
	 * {@link com.minigod.zero.customer.enums.ThawingType}
	 */
	private Integer thawingType;
	/**
	 * 理财账号
	 */
	private String accountId;
	/**
	 * 0：扣除冻结，1：冻结转可用
	 */
	private Integer type;
	/**
	 * 调用方业务id
	 */
	private String businessId;

	private String source;

	private Long custId;

	private String businessType;
}
