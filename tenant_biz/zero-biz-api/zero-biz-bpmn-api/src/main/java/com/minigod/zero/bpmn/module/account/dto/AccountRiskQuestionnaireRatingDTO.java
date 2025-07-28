package com.minigod.zero.bpmn.module.account.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
/**
 * 风险测评问卷和风险等级关联参数
 *
 * @author eric
 * @since 2024-09-02 09:43:09
 */
@Data
@ApiModel(value = "风险测评问卷和题目关联参数对象")
public class AccountRiskQuestionnaireRatingDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 问卷ID
	 */
	@ApiModelProperty(value = "问卷ID")
	private Long questionnaireId;
	/**
	 * 风险等级
	 */
	@ApiModelProperty(value = "风险等级")
	private Integer rating;
	/**
	 * 风险等级名称
	 */
	@ApiModelProperty(value = "风险等级名称")
	private String ratingName;
	/**
	 * 等级顺序
	 */
	@ApiModelProperty(value = "等级顺序")
	private Integer sort;
	/**
	 * 分数下限
	 */
	@ApiModelProperty(value = "分数下限")
	private Float scoreLower;
	/**
	 * 分数上限
	 */
	@ApiModelProperty(value = "分数上限")
	private Float scoreUpper;
}
