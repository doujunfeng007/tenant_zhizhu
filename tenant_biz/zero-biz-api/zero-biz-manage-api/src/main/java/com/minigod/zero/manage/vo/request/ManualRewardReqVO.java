package com.minigod.zero.manage.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 手动发奖请求入参
 *
 * @author eric
 * @since 2024-12-24 13:32:08
 */
@Data
@ApiModel(description = "手动发奖请求入参")
public class ManualRewardReqVO implements Serializable {
	/**
	 * 序列ID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 用户ID
	 */
	@ApiModelProperty(value = "用户ID")
	private Integer userId;
	/**
	 * 奖品ID
	 */
	@ApiModelProperty(value = "奖品ID")
	private Integer activeCfgItemId;
	/**
	 * 奖品名称
	 */
	@ApiModelProperty(value = "奖品名称")
	private String activeItemName;
	/**
	 * 奖品类型
	 */
	@ApiModelProperty(value = "奖品类型")
	private Integer rewardType;
	/**
	 * 行情产品ID
	 */
	@ApiModelProperty(value = "行情产品ID")
	private Integer packageId;
	/**
	 * 免佣总天数
	 */
	@ApiModelProperty(value = "免佣总天数")
	private Integer freeDays;
	/**
	 * 免佣类型
	 */
	@ApiModelProperty(value = "免佣类型")
	private Integer CommissionType;
	/**
	 * 奖励现金总金额
	 */
	@ApiModelProperty(value = "奖励现金总金额")
	private BigDecimal totalMoney;
	/**
	 * 操作人员ID
	 */
	@ApiModelProperty(value = "操作人员ID")
	private Long oprId;
	/**
	 * 操作人员名称
	 */
	@ApiModelProperty(value = "操作人员名称")
	private String oprName;
	/**
	 * 申请发放原因
	 */
	@ApiModelProperty(value = "申请发放原因")
	private String oprReason;
	/**
	 * 奖励条件
	 */
	@ApiModelProperty(value = "奖励条件")
	private String rewardCondition;
	/**
	 * 资产(股票)ID
	 */
	@ApiModelProperty(value = "资产(股票)ID")
	private String assetId;
	/**
	 * 股数
	 */
	@ApiModelProperty(value = "股数")
	private Integer quantity;
	/**
	 * 发奖来源
	 */
	@ApiModelProperty(value = "发奖来源")
	private String sendSource;
	/**
	 * 使用天数
	 */
	@ApiModelProperty(value = "使用天数")
	private Integer useDay;
	/**
	 * 奖品子类型
	 */
	@ApiModelProperty(value = "奖品子类型")
	private Integer rewardSubtype;
	/**
	 * 奖励金额
	 */
	@ApiModelProperty(value = "奖励金额")
	private BigDecimal rewardMoney;
}
