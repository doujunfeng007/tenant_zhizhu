package com.minigod.zero.bpmn.module.account.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
/**
 * 风险测评题选项多条件查询参数
 *
 * @author eric
 * @since 2024-08-20 13:52:05
 */
@Data
@ApiModel(value = "风险测评题选项多条件查询参数对象")
public class AccountRiskQuestionOptionDTO implements Serializable {
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
	/**
	 * 选项内容
	 */
	@ApiModelProperty(value = "选项内容")
	private String optionValue;
	/**
	 * 多语言标识
	 */
	@ApiModelProperty(value = "多语言标识")
	private String lang;
	/**
	 * 选项分值
	 */
	@ApiModelProperty(value = "选项分值")
	private Integer optionScore;
	/**
	 * 选项ID
	 */
	@ApiModelProperty(value = "选项ID")
	private Integer optionId;
	/**
	 * 0.有效，1.失效
	 */
	@ApiModelProperty(value = "0.有效，1.失效")
	private Integer flag;
}
