package com.minigod.zero.bpmn.module.account.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 开户资料个人信息修改返回对象
 *
 * @author eric
 * @since 2024-08-05 15:47:32
 */
@Data
@ApiModel(value = "AccountPersonalInfoModifyVO", description = "开户资料个人信息修改返回对象")
public class AccountPersonalInfoModifyVO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 住所电话
	 */
	@ApiModelProperty(value = "住所电话")
	private String familyPhone;
	/**
	 * 婚姻：1.单身 2.已婚
	 */
	@ApiModelProperty(value = "婚姻：1.单身 2.已婚")
	private Integer maritalStatus;

	/**
	 * 国籍
	 */
	@ApiModelProperty(value = "国籍")
	private String nationality;

	/**
	 * 身份证生效日期
	 */
	@ApiModelProperty(value = "身份证生效日期")
	private String idCardValidDateStart;

	/**
	 * 身份证失效日期
	 */
	@ApiModelProperty(value = "身份证失效日期")
	private String idCardValidDateEnd;

	/**
	 * 教育程度:1.小学或以下 2.中学 3.大专 4.大学或以上
	 */
	@ApiModelProperty(value = "教育程度:1.小学或以下 2.中学 3.大专 4.大学或以上")
	private Integer educationLevel;
	/**
	 * 电子邮箱
	 */
	@ApiModelProperty(value = "电子邮箱")
	private String email;
}
