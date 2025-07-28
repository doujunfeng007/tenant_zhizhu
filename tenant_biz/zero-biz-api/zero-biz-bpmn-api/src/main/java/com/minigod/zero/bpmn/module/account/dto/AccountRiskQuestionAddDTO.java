package com.minigod.zero.bpmn.module.account.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 添加风险测评问题参数
 *
 * @author eric
 * @since 2024-08-20 16:31:28
 */
@Data
@ApiModel(value = "添加风险测评问题参数对象")
public class AccountRiskQuestionAddDTO {
	@ApiModelProperty(value = "风险测评问题")
	private AccountRiskQuestionDTO acctRiskQuestion;
	@ApiModelProperty(value = "风险测评问题选项")
	private List<AccountRiskQuestionOptionDTO> acctRiskQuestionOption;
}
