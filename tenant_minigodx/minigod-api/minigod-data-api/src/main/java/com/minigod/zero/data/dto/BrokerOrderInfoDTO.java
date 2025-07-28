package com.minigod.zero.data.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName: com.minigod.zero.customer.dto.BrokerOrderInfoDTO
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/9/24 18:12
 * @Version: 1.0
 */
@Data
public class BrokerOrderInfoDTO {

	private String orderId;
	/**
	 * 客户id
	 */
	private Long custId;

	/**
	 * 交易金额
	 */
	private BigDecimal amount;

	/**
	 * 币种
	 */
	private String currency;
	/**
	 * 兑换汇率
	 */
	private BigDecimal rate;

}
