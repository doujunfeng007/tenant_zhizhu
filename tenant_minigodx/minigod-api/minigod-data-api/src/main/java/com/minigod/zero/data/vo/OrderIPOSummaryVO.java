package com.minigod.zero.data.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * IPO发行订单认购统计
 *
 * @author eric
 * @since 2024-11-06 19:41:05
 */
@Data
@ApiModel(value = "IPO发行订单认购统计")
public class OrderIPOSummaryVO {

	@ApiModelProperty(value = "产品ISIN")
	private String isin;

	@ApiModelProperty(value = "认购人数")
	private Integer subscribeCount;

	@ApiModelProperty(value = "距离发行日期剩余天数")
	private Integer daysToIssueDate;

	@ApiModelProperty(value = "已认购总金额")
	private BigDecimal totalAmount;
}
