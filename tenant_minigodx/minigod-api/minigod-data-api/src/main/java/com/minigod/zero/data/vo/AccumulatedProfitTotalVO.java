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
@ApiModel(description = "累计收益统计")
public class AccumulatedProfitTotalVO {
	@ApiModelProperty(value = "已实现收益(HDK)")
	private BigDecimal hkdRealizedGainLoss;

	@ApiModelProperty(value = "已实现收益(USD)")
	private BigDecimal usdRealizedGainLoss;

	@ApiModelProperty(value = "已实现收益(CNY)")
	private BigDecimal cnyRealizedGainLoss;

	@ApiModelProperty(value = "总收益(已实现+未实现)(HDK)")
	private BigDecimal hkdTotalGainLoss;

	@ApiModelProperty(value = "总收益(已实现+未实现)(USD)")
	private BigDecimal usdTotalGainLoss;

	@ApiModelProperty(value = "总收益(已实现+未实现)(CNY)")
	private BigDecimal cnyTotalGainLoss;

	@ApiModelProperty(value = "累计现金分红(HDK)")
	private BigDecimal hkdAccumulatedCashDividends;

	@ApiModelProperty(value = "累计现金分红(USD)")
	private BigDecimal usdAccumulatedCashDividends;

	@ApiModelProperty(value = "累计现金分红(CNY)")
	private BigDecimal cnyAccumulatedCashDividends;


	@ApiModelProperty(value = "已实现收益(所有币种转换为港币后的总额-HDK)")
	private BigDecimal hkdTotalRealizedGainLoss;


	@ApiModelProperty(value = "总收益(已实现+未实现)(所有币种转换为港币后的总额-HDK)")
	private BigDecimal hkdTotalAmountGainLoss;


	@ApiModelProperty(value = "累计现金分红(所有币种转换为港币后的总额-HDK)")
	private BigDecimal hkdTotalAccumulatedCashDividends;
}
