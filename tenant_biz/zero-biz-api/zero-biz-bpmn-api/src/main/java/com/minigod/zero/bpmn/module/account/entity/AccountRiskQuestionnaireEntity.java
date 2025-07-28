package com.minigod.zero.bpmn.module.account.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 风险评测问卷
 */
@Data
@TableName("acct_risk_questionnaire")
@ApiModel(value = "AccountRiskQuestionnaire对象", description = "风险评测问卷")
public class AccountRiskQuestionnaireEntity extends TenantEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 问卷名称
	 */
	@ApiModelProperty(value = "问卷名称")
	private String questionnaireName;

	/**
	 * 问卷类型
	 */
	@ApiModelProperty(value = "问卷类型")
	private Integer questionnaireType;

	/**
	 * 问卷描述
	 */
	@ApiModelProperty(value = "问卷描述")
	private String questionnaireDesc;

	/**
	 * 多语言标识
	 */
	@ApiModelProperty(value = "多语言标识")
	private String lang;
}
