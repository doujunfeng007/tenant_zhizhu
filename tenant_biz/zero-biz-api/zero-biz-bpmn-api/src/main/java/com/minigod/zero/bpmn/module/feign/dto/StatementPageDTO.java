package com.minigod.zero.bpmn.module.feign.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName: com.minigod.zero.customer.dto.StatementPageVO
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/5/31 16:07
 * @Version: 1.0
 */
@Data
public class StatementPageDTO {

	@ApiModelProperty(value = "类型  2月结单  1日结单")
	private Integer type;

	@ApiModelProperty(value = "页码")
	private Integer start;

	@ApiModelProperty(value = "页大小")
	private Integer count;

	@ApiModelProperty(value = "用户id")
	private Long custId;

	/**
	 * 开始时间
	 */
	@ApiModelProperty(value = "date")
	private Date startDate;

	/**
	 * 结束时间
	 */
	@ApiModelProperty(value = "endDate")
	private Date endDate;

}
