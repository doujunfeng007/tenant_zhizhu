package com.minigod.zero.bpm.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value = "换汇提交申请信息对象", description = "换汇提交申请信息")
public class MoneyExchangeReqDTO {

	/**
	 * 源货币
	 */
	@ApiModelProperty(value = "兑出币种[0-人民币CNY 1-美元USD 2-港币HKD]")
	private Integer currencyOut;

	/**
	 * 目标货币
	 */
	@ApiModelProperty(value = "兑入币种[0-人民币CNY 1-美元USD 2-港币HKD]")
	private Integer currencyIn;

	/**
	 * 源货币数量
	 */
	@ApiModelProperty(value = "申请兑出金额")
	private BigDecimal amountOut;

	/**
	 * 目标货币数量
	 */
	@ApiModelProperty(value = "申请兑入金额")
	private BigDecimal amountIn;

	@ApiModelProperty(value = "参考兑换汇率")
	private BigDecimal exchangeRate;

	@ApiModelProperty(value = "实际兑换汇率")
	private BigDecimal actualExchangeRate;

	@ApiModelProperty(value = "可提余额")
	private BigDecimal availableBalance;

	/**
	 * 密码
	 */
	@ApiModelProperty(value = "密码")
	private String pwd;

	@ApiModelProperty(value = "用户号")
	private Long custId;
}
