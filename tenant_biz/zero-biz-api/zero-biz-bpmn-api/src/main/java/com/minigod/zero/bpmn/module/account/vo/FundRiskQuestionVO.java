package com.minigod.zero.bpmn.module.account.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 风险测评问题视图对象
 *
 * @author eric
 * @since 2024-08-20 14:11:05
 */
@Data
@ApiModel(value = "风险测评问题视图对象")
public class FundRiskQuestionVO implements Serializable {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "问题ID")
	private Integer questionId;
	@ApiModelProperty(value = "问题")
	private String question;
	@ApiModelProperty(value = "选项内容")
	private String optionValue;
}
