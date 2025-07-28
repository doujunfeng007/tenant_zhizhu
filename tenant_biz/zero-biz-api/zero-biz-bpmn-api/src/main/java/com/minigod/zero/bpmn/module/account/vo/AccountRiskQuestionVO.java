package com.minigod.zero.bpmn.module.account.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 风险评测题库 视图实体类
 *
 * @author eric
 * @since 2024-08-20 11:25:05
 */
@Data
@ApiModel(value = "风险评测题库 视图对象", description = "风险评测题库 视图对象")
public class AccountRiskQuestionVO implements Serializable {
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
