package com.minigod.zero.manage.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;

@Data
public class CustFundRiskVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 风险评分
	 */
	@ApiModelProperty(value = "风险评分")
	@NotNull(message = "风险评分不能为空")
	private Integer riskScore;
	/**
	 * 风险评级[1.保守型 2.稳健型 3.平衡型 4.增长型 5.进取型]
	 */
	@ApiModelProperty(value = "风险评级[1.保守型 2.稳健型 3.平衡型 4.增长型 5.进取型]")
	@NotNull(message = "风险评级不能为空")
	private Integer riskType;
	/**
	 * 题库类型[1.个人户题库，2.公司户题库]
	 */
	@ApiModelProperty(value = "题库类型[1.个人户题库，2.公司户题库]")
	@NotNull(message = "题库类型不能为空")
	private Integer questionType;
	/**
	 * 选项内容
	 */
	@ApiModelProperty(value = "选项内容")
	@NotBlank(message = "选项内容不能为空")
	private String tempData;
}
