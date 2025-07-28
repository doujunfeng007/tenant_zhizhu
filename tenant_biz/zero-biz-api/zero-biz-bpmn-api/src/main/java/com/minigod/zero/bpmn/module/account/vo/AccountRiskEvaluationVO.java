package com.minigod.zero.bpmn.module.account.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 风险评测记录表 视图实体类
 *
 * @author eric
 * @since 2024-08-20 11:20:01
 */
@Data
@ApiModel(value = "风险评测记录表 视图对象", description = "风险评测记录表 视图对象")
public class AccountRiskEvaluationVO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 主键id
	 */
	@ApiModelProperty(value = "主键id")
	private Long id;
	/**
	 * 问卷ID
	 */
	@ApiModelProperty(value = "问卷ID")
	private Long questionnaireId;
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
	 * 用户号
	 */
	@ApiModelProperty(value = "用户号")
	private Long custId;
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
	 * 语言
	 */
	@ApiModelProperty(value = "语言")
	private String lang;
	/**
	 * 风险评级
	 */
	@ApiModelProperty(value = "风险评级")
	private Integer riskType;
	/**
	 * 风险等级名称
	 */
	@ApiModelProperty(value = "风险等级名称")
	private String riskTypeName;
	/**
	 * 失效日期
	 */
	@ApiModelProperty(value = "失效日期")
	private Date expiryDate;
	/**
	 * 是否已失效 1.已失效,0.未失效
	 */
	@ApiModelProperty(value = "是否已失效:1.已失效,0.未失效")
	private Integer isExpire;
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
	/**
	 * 是否可重测
	 */
	@ApiModelProperty(value = "是否可重测")
	private boolean canRetest;
	/**
	 * 创建时间
	 */
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@ApiModelProperty("创建时间")
	private Date createTime;
}
