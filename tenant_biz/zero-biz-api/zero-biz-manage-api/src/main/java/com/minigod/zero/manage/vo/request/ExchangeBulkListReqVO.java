package com.minigod.zero.manage.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询批量兑换码请求参数
 *
 * @author eric
 * @since 2024-12-27 10:50:01
 */
@Data
@ApiModel(value = "查询批量兑换码请求参数")
public class ExchangeBulkListReqVO implements Serializable {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "开始日期")
	private String startDate;
	@ApiModelProperty(value = "结束日期")
	private String endDate;
	@ApiModelProperty(value = "创建人")
	private String oprName;
	@ApiModelProperty(value = "是否使用(0:未使用 1:已使用)")
	private Integer useStatus = -1;
}
