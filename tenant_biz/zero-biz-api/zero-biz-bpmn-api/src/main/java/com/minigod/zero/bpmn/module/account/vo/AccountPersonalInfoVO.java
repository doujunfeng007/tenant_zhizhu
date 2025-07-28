package com.minigod.zero.bpmn.module.account.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 开户资料个人信息返回对象
 *
 * @author eric
 * @since 2024-08-05 15:47:32
 */
@Data
@ApiModel(value = "AccountPersonalInfoVO", description = "开户资料个人信息返回对象")
public class AccountPersonalInfoVO implements Serializable {
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
	 * 电子邮箱
	 */
	@ApiModelProperty(value = "电子邮箱")
	private String email;

	/**
	 * 客户中文名
	 */
	@ApiModelProperty(value = "客户中文名")
	private String clientName;

	/**
	 * 姓氏
	 */
	@ApiModelProperty(value = "姓氏")
	private String familyName;
	/**
	 * 名字
	 */
	@ApiModelProperty(value = "名字")
	private String givenName;

	/**
	 * 客户英文名
	 */
	@ApiModelProperty(value = "客户英文名")
	private String clientNameSpell;

	/**
	 * 英文名字
	 */
	@ApiModelProperty(value = "英文名字")
	private String givenNameSpell;
	/**
	 * 英文姓氏
	 */
	@ApiModelProperty(value = "英文姓氏")
	private String familyNameSpell;

	/**
	 * 证件号码
	 */
	@ApiModelProperty(value = "证件号码")
	private String idNo;
	/**
	 * 证件类型[0=未知 1=大陆居民身份证 2=香港居民身份证 3=护照 4=驾驶证]
	 */
	@ApiModelProperty(value = "证件类型[0=未知 1=大陆居民身份证 2=香港居民身份证 3=护照 4=驾驶证]")
	private Integer idKind;
	/**
	 * 性别[0=男 1=女 2=其它]
	 */
	@ApiModelProperty(value = "性别[0=男 1=女 2=其它]")
	private Integer sex;
	/**
	 * 生日日期
	 */
	@ApiModelProperty(value = "生日日期")
	private String birthday;

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

}
