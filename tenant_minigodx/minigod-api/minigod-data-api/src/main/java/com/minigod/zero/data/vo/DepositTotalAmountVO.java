package com.minigod.zero.data.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 客户入金总额统计VO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "客户入金总额统计VO")
public class DepositTotalAmountVO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 港币总额
	 */
	@ApiModelProperty(value = "港币总额")
	private BigDecimal hkdAmount;

	/**
	 * 美元总额
	 */
	@ApiModelProperty(value = "美元总额")
	private BigDecimal usdAmount;

	/**
	 * 人民币总额
	 */
	@ApiModelProperty(value = "人民币总额")
	private BigDecimal cnyAmount;

	/**
	 * 所有币种转换为港币后的总额
	 */
	@ApiModelProperty(value = "所有币种转换为港币后的总额")
	private BigDecimal totalHkdAmount;
}
