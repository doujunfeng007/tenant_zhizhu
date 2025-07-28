package com.minigod.zero.bpmn.module.account.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 风险评测选项视图对象
 *
 * @author eric
 * @since 2024-08-20 14:10:05
 */
@Data
@ApiModel(value = "风险评测选项")
public class FundRiskQuestionOptionVO implements Serializable {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "问题ID")
	private Integer questionId;
	@ApiModelProperty(value = "选项ID")
	private Integer optionId;
	@ApiModelProperty(value = "选项内容")
	private String optionValue;
	@ApiModelProperty(value = "选项分值")
	private Integer optionScore;
}
