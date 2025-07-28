package com.minigod.zero.data.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 统计IPO 产品购买情况
 *
 * @author eric
 * @since 2024-11-06 13:32:05
 */
@Data
@ApiModel(value = "统计IPO 产品购买情况")
public class OrderStatisticsVO {
	/**
	 * 子账户ID
	 */
	@ApiModelProperty(value = "子账户ID")
	private String subAccountId;

	/**
	 * ISIN列表（逗号分隔）
	 */
	@ApiModelProperty(value = "ISIN列表（逗号分隔）")
	private String isin;

	/**
	 * 币种
	 */
	@ApiModelProperty(value = "币种")
	private String currency;

	/**
	 * 总金额
	 */
	@ApiModelProperty(value = "总金额")
	private BigDecimal totalAmount;

	/**
	 * 距离发行日期剩余天数
	 */
	@ApiModelProperty(value = "距离发行日期剩余天数")
	private Integer daysToIssueDate;

}
