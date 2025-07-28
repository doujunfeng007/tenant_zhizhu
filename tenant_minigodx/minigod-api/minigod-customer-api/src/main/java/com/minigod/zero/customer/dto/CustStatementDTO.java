package com.minigod.zero.customer.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName: com.minigod.zero.customer.dto.CustStatementDTO
 * @Description: 结单入参
 * @Author: linggr
 * @CreateDate: 2024/5/23 22:36
 * @Version: 1.0
 */
@Data
public class CustStatementDTO {
	@ApiModelProperty(value = "子账户id")
	private String subAccountId;

	@ApiModelProperty(value = "开始时间")
	private Date startDate;

	@ApiModelProperty(value = "时间")
	private Date date;

	@ApiModelProperty(value = "结束时间")
	private Date endDate;

	@ApiModelProperty(value = "租户id")
	private String tenantId;

	@ApiModelProperty(value = "custId")
	private String custId;
	private String accountId;
	private String taSubAccountId;

	/**
	 * 类型  2月结单  1日结单
	 */
	private Integer statementType;
}
