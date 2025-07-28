package com.minigod.zero.bpmn.module.account.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 风险评测题库-选项 视图
 *
 * @author eric
 * @since 2024-09-02 14:00:04
 */
@Data
@ApiModel(value = "风险评测题库及选项信息视图", description = "风险评测题库及选项信息视图")
public class AccountRiskQuestionAndOptionCountVO implements Serializable {
	@ApiModelProperty(value = "主键id")
	private Integer id;

	@ApiModelProperty(value = "问题")
	private String question;

	@ApiModelProperty(value = "问题ID")
	private Long questionId;

	@ApiModelProperty(value = "问题顺序")
	private Integer sort;

	@ApiModelProperty(value = "多语言标识")
	private String lang;

	@ApiModelProperty(value = "1.单选题,2.复选题")
	private Integer checkType;

	@ApiModelProperty(value = "1.单选题,2.复选题")
	private String checkTypeName;

	@ApiModelProperty(value = "1.个人户题库，2.公司户题库，3.PI认证户题库")
	private Integer questionType;

	@ApiModelProperty(value = "1.主观题,2.客观题")
	private Integer optType;

	@ApiModelProperty(value = "是否必填")
	private boolean isRequired;

	@ApiModelProperty(value = "答案数目")
	private Integer optionCount;

	@ApiModelProperty(value = "总分")
	private Float totalScore;
}
