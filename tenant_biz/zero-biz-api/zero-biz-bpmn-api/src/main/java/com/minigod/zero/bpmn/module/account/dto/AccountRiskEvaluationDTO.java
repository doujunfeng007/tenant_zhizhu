package com.minigod.zero.bpmn.module.account.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 风险评测结果查询参数
 *
 * @author eric
 * @since 2024-08-20 13:59:05
 */
@Data
@ApiModel(value = "风险评测结果查询参数")
public class AccountRiskEvaluationDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 用户ID
	 */
	@ApiModelProperty(value = "用户ID")
	private Long custId;
	/**
	 * 风险评测ID
	 */
	@ApiModelProperty(value = "风险评测ID")
	private Long id;
	/**
	 * 问题ID
	 */
	@ApiModelProperty(value = "问题ID")
	private Integer questionId;
	/**
	 * 问题类型
	 */
	@ApiModelProperty(value = "问题类型")
	private Integer questionType;
	/**
	 * 选项ID
	 */
	@ApiModelProperty(value = "选项ID")
	private Integer optionId;
	/**
	 * 多语言标识
	 */
	@ApiModelProperty(value = "多语言标识")
	private String lang;
}
