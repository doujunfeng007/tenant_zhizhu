package com.minigod.zero.bpmn.module.account.bo.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询CA认证失败的参数对象
 *
 * @author eric
 * @since 2024-07-11 11:05:01
 */
@Data
public class CABankVerifyQuery implements Serializable {
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

	@ApiModelProperty("租户ID")
	private String tenantId;
}
