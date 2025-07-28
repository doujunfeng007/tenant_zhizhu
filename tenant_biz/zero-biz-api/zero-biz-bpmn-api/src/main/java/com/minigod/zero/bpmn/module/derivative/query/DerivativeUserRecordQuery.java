package com.minigod.zero.bpmn.module.derivative.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分页查询条件
 *
 * @author eric
 * @since 2024-06-25 17:17:12
 */
@Data
public class DerivativeUserRecordQuery {
	@ApiModelProperty(value = "租户ID")
	private String tenantId;

	@ApiModelProperty(value = "用户ID")
	private Long custId;

	@ApiModelProperty(value = "用户中文名")
	private String clientName;

	@ApiModelProperty(value = "用户中文名拼音")
	private String clientNameSpell;

	@ApiModelProperty(value = "英文名字")
	private String giveName;

	@ApiModelProperty(value = "英文姓氏")
	private String familyName;

	@ApiModelProperty(value = "证件号码")
	private String idNo;

	@ApiModelProperty(value = "手机号")
	private String phoneNumber;
}
