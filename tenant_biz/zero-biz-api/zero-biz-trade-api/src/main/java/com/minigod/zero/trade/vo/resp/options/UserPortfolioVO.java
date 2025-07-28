package com.minigod.zero.trade.vo.resp.options;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chen
 * @ClassName UserPortfolioVO.java
 * @Description TODO
 * @createTime 2024年08月28日 14:20:00
 */
@Data
public class UserPortfolioVO {

	@ApiModelProperty(value = "期权代码")
	private String optionsCode;

	@ApiModelProperty(value = "期权名称")
	private String optionsName;

	@ApiModelProperty(value = "持仓数量")
	private Long amount;

	@ApiModelProperty(value = "成本价")
	private BigDecimal costPrice;

	@ApiModelProperty(value = "最新价")
	private BigDecimal lastPrice;

	@ApiModelProperty(value = "市值")
	private BigDecimal totalMarketValue;

	@ApiModelProperty(value = "收益")
	private BigDecimal incomeBalance;

	@ApiModelProperty(value = "收益率")
	private BigDecimal incomeRate;
}
