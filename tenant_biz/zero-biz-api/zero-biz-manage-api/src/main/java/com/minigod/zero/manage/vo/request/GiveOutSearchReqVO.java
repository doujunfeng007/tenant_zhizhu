package com.minigod.zero.manage.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 奖品发放查询请求参数
 *
 * @author eric
 * @date 2024-12-23 18:35:08
 */
@Data
@ApiModel(description = "奖品发放查询请求参数")
public class GiveOutSearchReqVO implements Serializable {
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
	 * 开始时间
	 */
	@ApiModelProperty(value = "开始时间")
	private Date startTime;
	/**
	 * 结束时间
	 */
	@ApiModelProperty(value = "结束时间")
	private Date endTime;
	/**
	 * 用户ID
	 */
	@ApiModelProperty(value = "用户ID")
	private Integer userId;
	/**
	 * 状态
	 */
	@ApiModelProperty(value = "状态")
	private Integer rewardStatus;
	/**
	 * 渠道
	 */
	@ApiModelProperty(value = "渠道")
	private String channel;
	/**
	 * 手机号码
	 */
	@ApiModelProperty(value = "手机号码")
	private String phoneNumber;
}
