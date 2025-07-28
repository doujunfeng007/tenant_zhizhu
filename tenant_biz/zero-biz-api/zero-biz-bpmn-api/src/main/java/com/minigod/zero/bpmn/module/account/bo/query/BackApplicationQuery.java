package com.minigod.zero.bpmn.module.account.bo.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询退回给客户的申请列表
 *
 * @author eric
 * @since 2024-07-11 10:12:14
 */

@Data
public class BackApplicationQuery implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("流水号")
	private String applicationId;

	@ApiModelProperty("客户中文名")
	private String clientName;

	@ApiModelProperty("客户英文名")
	private String clientNameSpell;

	@ApiModelProperty("证件号码")
	private String idNo;

	@ApiModelProperty("手机号")
	private String phoneNumber;

	@ApiModelProperty("客户ID")
	private Long userId;

	@ApiModelProperty("状态")
	private String status;

	@ApiModelProperty("租户ID")
	private String tenantId;
}
