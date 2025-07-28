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
public class DerivativeAssessmentQuery {
	/**
	 * 租户ID
	 */
	@ApiModelProperty(value = "租户ID")
	private String tenantId;
	/**
	 * 用户ID
	 */
	@ApiModelProperty(value = "用户ID")
	private Long custId;
}
