package com.minigod.zero.bpmn.module.account.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 风险评测记录表 实体类
 *
 * @author eric
 * @since 2024-08-20 11:01:00
 */
@Data
@TableName("acct_risk_evaluation")
@ApiModel(value = "AcctRiskEvaluation对象", description = "风险评测记录表")
public class AccountRiskEvaluationEntity extends TenantEntity {
	/**
	 * 用户号
	 */
	@ApiModelProperty(value = "用户号")
	private Long custId;
	/**
	 * 问卷ID
	 */
	@ApiModelProperty(value = "问卷ID")
	private Long questionnaireId;
	/**
	 * 主观题总分
	 */
	@ApiModelProperty(value = "主观题总分")
	private Integer subjectiveScore;
	/**
	 * 风险评分
	 */
	@ApiModelProperty(value = "风险评分")
	private String riskScore;
	/**
	 * 客观题总分
	 */
	@ApiModelProperty(value = "客观题总分")
	private Integer objectiveScore;
	/**
	 * 选项内容
	 */
	@ApiModelProperty(value = "选项内容")
	private String optionData;
	/**
	 * 风险评级
	 */
	@ApiModelProperty(value = "风险评级")
	private Integer riskType;
	/**
	 * 失效日期
	 */
	@ApiModelProperty(value = "失效日期")
	private Date expiryDate;
	/**
	 * 是否发送过期通知
	 */
	@ApiModelProperty(value = "是否发送过期通知")
	private Integer isPush;
	/**
	 * 0.未重测,1.已重测
	 */
	@ApiModelProperty(value = "0.未重测,1.已重测")
	private Integer retestSts;
}
