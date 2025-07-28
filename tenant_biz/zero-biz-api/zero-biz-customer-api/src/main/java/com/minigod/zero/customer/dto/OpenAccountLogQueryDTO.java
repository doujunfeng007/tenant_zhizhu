package com.minigod.zero.customer.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "客户开户日志查询参数")
public class OpenAccountLogQueryDTO {
	@ApiModelProperty(value = "关键字")
	private String keyword;
	@ApiModelProperty(value = "客户风险等级")
	private Integer riskLevel;
	@ApiModelProperty(value = "客户pi等级")
	private Integer piLevel;
	@ApiModelProperty(value = "客户开户状态:0所有,1.未开户 2.已开户 3.已入金,")
	private Integer openStatus;
	@ApiModelProperty(value = "开始时间")
	private String startTime;
	@ApiModelProperty(value = "结束时间")
	private String endTime;
}
