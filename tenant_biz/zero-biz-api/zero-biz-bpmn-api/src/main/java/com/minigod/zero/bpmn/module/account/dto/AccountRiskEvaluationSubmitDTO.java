package com.minigod.zero.bpmn.module.account.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 风险测评提交参数
 *
 * @author eric
 * @since 2024-08-20 15:35:05
 */
@Data
@ApiModel(value = "风险评测结果提交参数")
public class AccountRiskEvaluationSubmitDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 问卷ID
	 */
	@ApiModelProperty(value = "问卷ID")
	private Long questionnaireId;

	/**
	 * 风险评分
	 */
	@ApiModelProperty(value = "风险评分")
	private String riskScore;

	/**
	 * 风险评级
	 */
	@ApiModelProperty(value = "风险评级")
	private Integer riskType;

	/**
	 * 测评题目类型
	 */
	@ApiModelProperty(value = "测评题目类型")
	private Integer questionType;

	/**
	 * 测评数据
	 */
	@ApiModelProperty(value = "测评数据")
	private String tempData;
}
