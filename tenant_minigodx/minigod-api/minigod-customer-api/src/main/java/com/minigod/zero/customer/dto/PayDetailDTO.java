package com.minigod.zero.customer.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/8/27 13:28
 * @description：
 */
@Data
public class PayDetailDTO implements Serializable {
	/**
	 * {@link com.minigod.zero.customer.enums.ThawingType}
	 */
	private Integer thawingType;
	/**
	 * 金额
	 */
	private BigDecimal amount;
	/**
	 * {@link com.minigod.zero.customer.enums.FlowBusinessType}
	 */
	private Integer payType;
}
