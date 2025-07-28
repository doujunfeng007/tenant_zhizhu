package com.minigod.zero.data.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 出金总额统计VO
 *
 * @author eric
 * @date 2024-10-31 20:53:04
 */
@ApiModel(value = "客户出金总额统计VO")
@Data
public class WithdrawalTotalAmountVO {
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
