package com.minigod.zero.bpmn.module.account.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 风险评测题库-选项 视图实体类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@Data
@ApiModel(value = "风险评测题库-选项 视图", description = "风险评测题库-选项 视图")
public class AccountRiskQuestionOptionVO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 主键id
	 */
	@ApiModelProperty(value = "主键id")
	private Long id;
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
