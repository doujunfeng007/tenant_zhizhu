package com.minigod.zero.customer.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/6/20 13:49
 * @description：
 */
@Data
public class CapitalFlowVO {
	/**
	 * 创建时间
	 */
	private String createTime;
	/**
	 * 类型
	 */
	private String typeName;
	/**
	 * 币种
	 */
	private String currency;

	private String flag;
	/**
	 * 金额
	 */
	private BigDecimal amount;
}
