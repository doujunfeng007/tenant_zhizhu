package com.minigod.zero.data.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "出入金明细及总额统计")
@Data
public class DepositAndWithdrawalStatisticsDTO {
	/**
	 * 业务开始时间
	 */
	@ApiModelProperty(value = "业务开始时间")
	private String startTime;
	/**
	 * 业务结束时间
	 */
	@ApiModelProperty(value = "业务结束时间")
	private String endTime;
}
