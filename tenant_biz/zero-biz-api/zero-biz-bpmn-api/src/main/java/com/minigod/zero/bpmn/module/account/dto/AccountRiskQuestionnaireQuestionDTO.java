package com.minigod.zero.bpmn.module.account.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
/**
 * 风险测评问卷和题目关联参数
 *
 * @author eric
 * @since 2024-09-02 09:38:13
 */
@Data
@ApiModel(value = "风险测评问卷和题目关联参数对象")
public class AccountRiskQuestionnaireQuestionDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 问卷ID
	 */
	@ApiModelProperty(value = "问卷ID")
	private Long questionnaireId;
	/**
	 * 问题ID
	 */
	@ApiModelProperty(value = "问题ID")
	private Long questionId;
	/**
	 * 问题顺序
	 */
	@ApiModelProperty(value = "问题顺序")
	private Integer sort;
}
