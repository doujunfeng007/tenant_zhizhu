package com.minigod.zero.customer.dto;

import lombok.Data;

/**
 * 客户风险等级查询体
 *
 * @author zxq
 * @since 2024-04-11
 */
@Data
public class CustRiskInfoDTO {
	private static final long serialVersionUID = 1L;
	/**
	 * 客户名称
	 */
	private String clientName;

	/**
	 * 客户名称
	 */
	private String cellPhone;
}
