package com.minigod.zero.bpmn.module.account.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 风险测评题库多条件查询参数
 *
 * @author eric
 * @since 2024-08-20 13:43:05
 */
@Data
@ApiModel(value = "风险测评题库多条件查询参数对象")
public class AccountRiskQuestionDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 问题ID
	 */
	@ApiModelProperty(value = "问题ID")
	private Integer questionId;
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
