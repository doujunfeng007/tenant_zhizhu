package com.minigod.zero.cust.apivo.req;


import java.math.BigDecimal;

import lombok.Data;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/5/11 13:08
 * @description：
 */
@Data
public class AmountVO {
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
	 * {@link com.minigod.zero.biz.mkt.enums.ThawingType}
	 */
	private Integer thawingType;
	/**
	 * 理财账号
	 */
	private String accountId;
	/**
	 * 2：扣除冻结，3：冻结转可用
	 */
	private Integer type;
	/**
	 * 调用方业务id
	 */
	private String businessId;

}
