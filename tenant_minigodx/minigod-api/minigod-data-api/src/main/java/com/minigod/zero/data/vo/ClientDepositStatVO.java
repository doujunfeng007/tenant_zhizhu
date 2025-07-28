package com.minigod.zero.data.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 客户入金统计VO
 */
@Data
@ApiModel(value = "客户入金统计VO")
public class ClientDepositStatVO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 人民币总额
	 */
	@ApiModelProperty(value = "人民币总额")
	private BigDecimal cnyTotal;

	/**
	 * 美元总额
	 */
	@ApiModelProperty(value = "美元总额")
	private BigDecimal usdTotal;

	/**
	 * 港币总额
	 */
	@ApiModelProperty(value = "港币总额")
	private BigDecimal hkdTotal;

	/**
	 * 所有币种转换为港币后的总额
	 */
	@ApiModelProperty(value = "所有币种转换为港币后的总额")
	private BigDecimal totalInHKD;
}
