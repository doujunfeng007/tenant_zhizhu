package com.minigod.zero.bpmn.module.account.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 风险测评问卷及题目
 *
 * @author eric
 * @since 2024-08-30 20:22:04
 */
@Data
@ApiModel(value = "风险测评问卷及题目")
public class AccountRiskQuestionnaireAndQuestionVO implements Serializable {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "风险测评问卷")
	private AccountRiskQuestionnaireVO accountRiskQuestionnaireVO;

	@ApiModelProperty(value = "风险测评题目")
	private List<AccountRiskQuestionVO> accountRiskQuestionVOs;
}
