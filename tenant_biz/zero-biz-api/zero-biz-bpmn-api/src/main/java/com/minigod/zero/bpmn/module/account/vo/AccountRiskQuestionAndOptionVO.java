package com.minigod.zero.bpmn.module.account.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 风险评测题库-选项 视图
 *
 * @author eric
 * @since 2024-08-20 19:29:04
 */
@Data
@ApiModel(value = "风险评测题库-选项 视图", description = "风险评测题库-选项 视图")
public class AccountRiskQuestionAndOptionVO implements Serializable {

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

	@ApiModelProperty(value = "1.个人户题库，2.公司户题库，3.PI认证户题库")
	private Integer questionType;

	@ApiModelProperty(value = "1.主观题,2.客观题")
	private Integer optType;

	@ApiModelProperty(value = "选项")
	private List<OptionVO> options;

	@Data
	public class OptionVO implements Serializable {

		@ApiModelProperty(value = "选项ID")
		private Integer optionId;

		@ApiModelProperty(value = "选项顺序")
		private Integer sort;

		@ApiModelProperty(value = "选项分值")
		private Integer optionScore;

		@ApiModelProperty(value = "选项内容")
		private String optionValue;
	}
}
