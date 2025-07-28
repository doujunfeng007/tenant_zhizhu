package com.minigod.zero.bpmn.module.account.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 风险评测题库-选项 实体类
 *
 * @author eric
 * @since 2024-08-20 11:14:05
 */
@Data
@TableName("acct_risk_question_option")
@ApiModel(value = "AcctRiskQuestionOption对象", description = "风险评测题库-选项")
public class AccountRiskQuestionOptionEntity extends TenantEntity {
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
