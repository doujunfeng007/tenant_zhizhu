package com.minigod.zero.trade.vo.req.options;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chen
 * @ClassName JournalOrdersReq.java
 * @Description TODO
 * @createTime 2024年08月28日 15:17:00
 */
@Data
public class JournalOrdersReq {

	@ApiModelProperty(value = "开始时间")
	private String startTime;

	@ApiModelProperty(value = "结束时间")
	private String endTime;

	@ApiModelProperty(value = "状态,多个之间用逗号隔开")
	private String status;


}
