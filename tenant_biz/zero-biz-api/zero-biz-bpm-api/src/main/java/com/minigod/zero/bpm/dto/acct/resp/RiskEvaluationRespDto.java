package com.minigod.zero.bpm.dto.acct.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 风险测评结果
 */
@Data
public class RiskEvaluationRespDto implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@ApiModelProperty(value = "主键ID")
	private Long id;
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
	@ApiModelProperty(value = "创建时间")
	private Date createTime;
	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间")
	private Date updateTime;

}
