package com.minigod.zero.manage.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 优惠券激活码查询参数
 */
@ApiModel(description = "优惠券激活码查询参数")
@Data
public class ExchangeCodeReqVO implements Serializable {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "开始时间")
	private String startDate;
	@ApiModelProperty(value = "结束时间")
	private String endDate;
	@ApiModelProperty(value = "操作人")
	private String oprName;
	@ApiModelProperty(value = "记录状态")
	private Integer recordStatus = -1;
	@ApiModelProperty(value = "开户状态")
	private Integer openStatus = -1;
	@ApiModelProperty(value = "入金状态")
	private Integer depositStatus = -1;
	@ApiModelProperty(value = "使用状态")
	private Integer useStatus = -1;
	@ApiModelProperty(value = "优惠券管理ID")
	private Long manageId;
	@ApiModelProperty(value = "手机号码")
	private String phoneNumber;
	@ApiModelProperty(value = "用户ID")
	private Integer userId;
	@ApiModelProperty(value = "激活码")
	private String exchangeCode;
	@ApiModelProperty(value = "序列号")
	private String serialNum;
	@ApiModelProperty(value = "渠道ID")
	private String channelId;
	@ApiModelProperty(value = "卡券类型")
	private Integer cardType = 0;
}
