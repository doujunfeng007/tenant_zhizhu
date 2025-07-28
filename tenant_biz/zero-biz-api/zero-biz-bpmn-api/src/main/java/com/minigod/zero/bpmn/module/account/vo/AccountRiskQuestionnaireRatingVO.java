package com.minigod.zero.bpmn.module.account.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "风险测评问卷及风险等级关联")
@Builder
public class AccountRiskQuestionnaireRatingVO implements Serializable {
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
