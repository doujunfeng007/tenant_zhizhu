package com.minigod.zero.bpmn.module.account.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 风险评测暂存记录
 *
 * @author eric
 * @since 2024-08-20 19:41:05
 */
@Data
@ApiModel(value = "风险评测暂存记录 视图对象", description = "风险评测暂存记录 视图对象")
public class AccountRiskEvaluationTempVO implements Serializable {
	@ApiModelProperty(value = "用户号")
	private Long custId;
	@ApiModelProperty(value = "问卷ID")
	private Long questionnaireId;
	@ApiModelProperty(value = "选项内容")
	private String tempData;
	@ApiModelProperty("创建时间")
	private Date time;
}
