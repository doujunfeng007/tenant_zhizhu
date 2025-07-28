package com.minigod.zero.bpmn.module.account.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 风险测评问卷及风险等级关联
 */
@Data
@TableName("acct_risk_questionnaire_rating")
@ApiModel(value = "AccountRiskQuestionnaireRating对象", description = "风险测评问卷及风险等级关联")
public class AccountRiskQuestionnaireRatingEntity extends TenantEntity {
	/**
	 * 问卷ID
	 */
	@ApiModelProperty(value = "问卷ID")
	private Long questionnaireId;
	/**
	 * 风险等级
	 */
	@ApiModelProperty(value = "风险等级")
	private Integer rating;
	/**
	 * 风险等级名称
	 */
	@ApiModelProperty(value = "风险等级名称")
	private String ratingName;
	/**
	 * 问题顺序
	 */
	@ApiModelProperty(value = "问题顺序")
	private Integer sort;
	/**
	 * 分数下限
	 */
	@ApiModelProperty(value = "分数下限")
	private Float scoreLower;
	/**
	 * 分数上限
	 */
	@ApiModelProperty(value = "分数上限")
	private Float scoreUpper;
}
