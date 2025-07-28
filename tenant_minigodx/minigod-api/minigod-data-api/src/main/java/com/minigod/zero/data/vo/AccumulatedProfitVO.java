package com.minigod.zero.data.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 累计收益统计
 *
 * @author eric
 * @date 2024-10-29 16:27:08
 */
@Data
@ApiModel(value = "累计收益统计")
public class AccumulatedProfitVO {
	@ApiModelProperty(value = "币种")
	private String currency;

	@ApiModelProperty(value = "已实现收益")
	private BigDecimal realizedGainLoss;

	@ApiModelProperty(value = "总收益(已实现+未实现)")
	private BigDecimal totalGainLoss;

	@ApiModelProperty(value = "累计现金分红")
	private BigDecimal accumulatedCashDividends;
}
