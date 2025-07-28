package com.minigod.zero.customer.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/9/3 18:12
 * @description：
 */
@Data
public class CustomerWithdrawalVO {
	private Long custId;
	/**
	 * 理财账号
	 */
	private String clientId;
	/**
	 * 客户名称
	 */
	private String clientName;
	/**
	 * 客户英文名
	 */
	private String clientNameSpell;
	/**
	 * 手机号
	 */
	private String mobile;
	private BigDecimal hkdAvailable = BigDecimal.ZERO;
	private BigDecimal hkdFreeze = BigDecimal.ZERO;
	private BigDecimal hkdIntransit = BigDecimal.ZERO;
	private BigDecimal usdAvailable = BigDecimal.ZERO;
	private BigDecimal usdFreeze = BigDecimal.ZERO;
	private BigDecimal usdIntransit = BigDecimal.ZERO;
	private BigDecimal cnyAvailable = BigDecimal.ZERO;
	private BigDecimal cnyFreeze = BigDecimal.ZERO;
	private BigDecimal cnyIntransit = BigDecimal.ZERO;
}
