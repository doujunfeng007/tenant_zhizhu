package com.minigod.zero.bpmn.module.account.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 风险评测题库 实体类
 *
 * @author eric
 * @since 2024-08-20 11:10:21
 */
@Data
@TableName("acct_risk_question")
@ApiModel(value = "AcctRiskQuestion对象", description = "风险评测题库")
public class AccountRiskQuestionEntity extends TenantEntity {
	/**
	 * 问题ID
	 */
	@ApiModelProperty(value = "问题ID")
	private Long questionId;
	/**
	 * 评测问题
	 */
	@ApiModelProperty(value = "评测问题")
	private String question;
	/**
	 * 问题顺序
	 */
	@ApiModelProperty(value = "问题顺序")
	private Integer sort;
	/**
	 * 多语言标识
	 */
	@ApiModelProperty(value = "多语言标识")
	private String lang;
	/**
	 * 1.个人户题库，2.公司户题库
	 */
	@ApiModelProperty(value = "1.个人户题库，2.公司户题库，3.PI认证户题库")
	private Integer questionType;
	/**
	 * 1.主观题,2.客观题
	 */
	@ApiModelProperty(value = "1.主观题,2.客观题")
	private Integer optType;
	/**
	 * 1.单选题,2.复选题
	 */
	@ApiModelProperty(value = "1.单选题,2.复选题")
	private Integer checkType;
	/**
	 * 0.有效，1.失效
	 */
	@ApiModelProperty(value = "0.有效，1.失效")
	private Integer flag;
}
