package com.minigod.zero.bpmn.module.feign.dto;

import com.minigod.zero.bpmn.module.feign.enums.ThawingType;
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
	 * {@link ThawingType}
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

	private Long custId;

	/**
	 * 账号类型 STOCK-股票 FUND-基金
	 */
	private String businessType;
}
