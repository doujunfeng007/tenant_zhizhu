package com.minigod.zero.bpmn.module.account.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 风险测评问卷参数
 *
 * @author eric
 * @since 2024-09-02 09:38:13
 */
@Data
@ApiModel(value = "风险测评问卷参数对象")
public class AccountRiskQuestionnaireDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 问卷名称
	 */
	@ApiModelProperty(value = "问卷名称")
	private String questionnaireName;

	/**
	 * 问卷类型
	 */
	@ApiModelProperty(value = "问卷类型")
	private Integer questionnaireType;

	/**
	 * 问卷描述
	 */
	@ApiModelProperty(value = "问卷描述")
	private String questionnaireDesc;

	/**
	 * 多语言标识
	 */
	@ApiModelProperty(value = "多语言标识")
	private String lang;
}
