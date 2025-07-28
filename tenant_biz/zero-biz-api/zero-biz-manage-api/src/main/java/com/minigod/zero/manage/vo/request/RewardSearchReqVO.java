package com.minigod.zero.manage.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询奖品库列表请求参数
 *
 * @author eric
 * @version 1.0
 * @date 2024-12-23 15:20:06
 */
@Data
@ApiModel(description = "查询奖品库列表请求参数")
public class RewardSearchReqVO implements Serializable {
	private static final long serialVersionUID = -2260388125919493487L;
	/**
	 * 奖品ID
	 */
	@ApiModelProperty(value = "奖品ID")
	private Long id;
	/**
	 * 奖品名称
	 */
	@ApiModelProperty(value = "奖品名称")
	private String activeItemName;
	/**
	 * 活动ID
	 */
	@ApiModelProperty(value = "活动ID")
	private Long snActiveConfigId;
	/**
	 * 奖品类型: 1.免佣 3.行情 2.现金
	 */
	@ApiModelProperty(value = "奖品类型: 1.免佣 3.行情 2.现金")
	private Integer rewardType;
	/**
	 * 记录状态 0.有效 1.失效
	 */
	@ApiModelProperty(value = "记录状态 0.有效 1.失效")
	private Integer status;
	/**
	 * 活动类型
	 */
	@ApiModelProperty(value = "活动类型")
	private Integer activeType;
}
