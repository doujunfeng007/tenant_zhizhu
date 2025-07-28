package com.minigod.zero.manage.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class RiskQuestionOptionVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 选项ID
	 */
	@ApiModelProperty(value = "选项ID")
	private Long id;
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
	 * 选项分值
	 */
	@ApiModelProperty(value = "选项分值")
	private Integer optionScore;
}
