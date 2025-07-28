package com.minigod.zero.manage.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 奖励规则
 *
 * @author eric
 * @since 2024-12-25 14:49:05
 */
@ApiModel(description = "奖励规则")
@Data
public class RuleRewardVO implements Serializable {
	private static final long serialVersionUID = -2260388125919493487L;
	@ApiModelProperty(value = "主键ID")
	private Long id;
	@ApiModelProperty(value = "奖励类型：1.开户 2.入金 3.邀请")
	private Integer funType;
	@ApiModelProperty(value = "奖励计划类型：1.固定金额 2.百分比")
	private Integer planType;
	@ApiModelProperty(value = "开始金额")
	private BigDecimal startAmount;
	@ApiModelProperty(value = "结束金额")
	private BigDecimal endAmount;
	@ApiModelProperty(value = "奖励金额")
	private BigDecimal rewardMoney;
	@ApiModelProperty(value = "奖励佣金天数")
	private Integer commissionDay;
	@ApiModelProperty(value = "奖励ID")
	private Long rewardId;
	@ApiModelProperty(value = "股票简称")
	private String stkName;
	@ApiModelProperty(value = "股数")
	private Integer stkNum;
	@ApiModelProperty(value = "资产ID")
	private String assetId;
	@ApiModelProperty(value = "奖励资产ID")
	private Integer rewardAssetId;
	@ApiModelProperty(value = "奖励佣金天数ID")
	private Integer commissionDayId;
	@ApiModelProperty(value = "奖励金额ID")
	private Integer rewardMoneyId;
	@ApiModelProperty(value = "开始金额")
	private String startAmountStr;
	@ApiModelProperty(value = "结束金额")
	private String endAmountStr;
}
