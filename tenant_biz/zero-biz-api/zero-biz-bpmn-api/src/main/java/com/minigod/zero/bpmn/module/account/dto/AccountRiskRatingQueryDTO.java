package com.minigod.zero.bpmn.module.account.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 风险等级查询参数
 *
 * @author eric
 * @since 2024-09-03 16:46:05
 */
@Data
@ApiModel(value = "风险等级查询参数对象")
public class AccountRiskRatingQueryDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "问卷ID")
	private Long questionnaireId;
	@ApiModelProperty(value = "分数")
	private Float score;
}
