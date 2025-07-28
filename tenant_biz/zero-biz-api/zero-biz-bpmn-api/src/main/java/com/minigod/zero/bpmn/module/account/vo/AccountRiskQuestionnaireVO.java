package com.minigod.zero.bpmn.module.account.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "风险测评问卷")
public class AccountRiskQuestionnaireVO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 问卷id
	 */
	@ApiModelProperty(value = "问卷id")
	private Long id;
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
