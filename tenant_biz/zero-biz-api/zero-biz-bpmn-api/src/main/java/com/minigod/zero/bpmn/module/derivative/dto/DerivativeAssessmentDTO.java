package com.minigod.zero.bpmn.module.derivative.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 衍生及結構性投資產品的認識評估参数对象
 *
 * @author eric
 * @since 2024-06-20 16:51:08
 */
@Data
public class DerivativeAssessmentDTO {
	@ApiModelProperty(value = "主键")
	private Long id;
	@ApiModelProperty(value = "用户ID")
	private Long custId;
	@ApiModelProperty(value = "租户ID")
	private String tenantId;
	@ApiModelProperty(value = "选项项目")
	private String optionItem;
	@ApiModelProperty(value = "选项值")
	private String optionValue;
}
