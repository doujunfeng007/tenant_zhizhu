package com.minigod.zero.manage.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 奖品发放请求参数
 *
 * @author eric
 * @date 2024-12-23 19:24:08
 */
@Data
@ApiModel(description = "奖品发放请求参数")
public class GiveOutRewardReqVO implements Serializable {
	private static final long serialVersionUID = -2260388125919493487L;
	/**
	 * 用户ID数组
	 */
	@ApiModelProperty(value = "用户ID数组")
	private Integer[] userIds;
	/**
	 * 奖品ID数组
	 */
	@ApiModelProperty(value = "奖品ID数组")
	private Integer[] rewardIds;
	/**
	 * 发放原因
	 */
	@ApiModelProperty(value = "发放原因")
	private String oprReason;
	/**
	 * 发放来源
	 */
	@ApiModelProperty(value = "发放来源")
	private String sendSource;
	/**
	 * 奖品领取条件
	 */
	@ApiModelProperty(value = "奖品领取条件")
	private String rewardCondition;
	/**
	 * 领用天数
	 */
	@ApiModelProperty(value = "领用天数")
	private Integer useDay;
}
