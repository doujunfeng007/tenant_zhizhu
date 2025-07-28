package com.minigod.zero.customer.dto;

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

	@ApiModelProperty(value = "date")
	private Date startDate;

	@ApiModelProperty(value = "endDate")
	private Date endDate;
	/**
	 * 结单发送状态  0未发送 1已发送 2发送失败
	 */
	@ApiModelProperty(value = "status")
	private Integer status;

}
