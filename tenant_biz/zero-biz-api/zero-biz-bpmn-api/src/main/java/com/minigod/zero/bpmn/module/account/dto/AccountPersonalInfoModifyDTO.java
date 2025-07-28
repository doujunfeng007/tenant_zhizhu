package com.minigod.zero.bpmn.module.account.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 开户资料个人信息
 *
 * @author eric
 * @since 2024-08-05 15:47:32
 */
@Data
@ApiModel(value = "AccountPersonalPageInfoDTO", description = "开户资料个人信息")
public class AccountPersonalInfoModifyDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 城市编码
	 */
	@ApiModelProperty(value = "城市编码")
	private String cityCode;
	/**
	 * 区域编码
	 */
	@ApiModelProperty(value = "区域编码")
	private String areaCode;
	/**
	 * 区域编号
	 */
	@ApiModelProperty(value = "区域编号")
	private String areaNumber;
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
