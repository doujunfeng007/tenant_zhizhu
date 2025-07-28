package com.minigod.zero.bpmn.module.account.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 风险测评问卷及题目关联
 */
@Data
@TableName("acct_risk_questionnaire_question")
@ApiModel(value = "AccountRiskQuestionnaireQuestion对象", description = "风险测评问卷及题目关联")
public class AccountRiskQuestionnaireQuestionEntity extends TenantEntity {
	private static final long serialVersionUID = 1L;
	/**
	 * 问卷ID
	 */
	@ApiModelProperty(value = "问卷ID")
	private Long questionnaireId;
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
}
