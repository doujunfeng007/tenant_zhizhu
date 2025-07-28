package com.minigod.zero.bpmn.module.account.dto;

import com.minigod.zero.bpmn.module.account.entity.AccountIdentityConfirmModifyEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 身份确认信息修改参数
 *
 * @author eric
 * @since 2024-08-05 15:27:01
 */
@Data
@ApiModel(value = "AccountIdentityConfirmModifyDTO", description = "身份确认信息修改参数")
public class AccountIdentityConfirmModifyDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 是否证监会持牌或注册人士的雇员
	 */
	@ApiModelProperty(value = "是否证监会持牌或注册人士的雇员")
	private Boolean isRegisterEmployee;

	/**
	 * 中央编号
	 */
	@ApiModelProperty(value = "中央编号")
	private String centerCode;

	/**
	 * 注册机构
	 */
	@ApiModelProperty(value = "注册机构")
	private String organizationName;

	/**
	 * 您是否是iFund公司雇员
	 */
	@ApiModelProperty(value = "是否公司雇员")
	private Boolean isEmployed;

	/**
	 * 亲属姓名
	 */
	@ApiModelProperty(value = "亲属姓名")
	private String employedName;

	/**
	 * 亲属关系
	 */
	@ApiModelProperty(value = "亲属关系")
	private String employedRelation;

	/**
	 * 是否公司的高级管理人员或董事
	 */
	@ApiModelProperty(value = "是否公司的高级管理人员或董事")
	private Boolean isExecutive;

	/**
	 * 公司名称
	 */
	@ApiModelProperty(value = "公司名称")
	private String executiveCompanyName;

	/**
	 * 股票代码
	 */
	@ApiModelProperty(value = "股票代码")
	private String executiveCompanyCode;

	/**
	 * 是否违法或违反监管
	 */
	@ApiModelProperty(value = "违法或违反监管")
	private Boolean isIllegal;

	/**
	 * 违法或违反监管具体情况
	 */
	@ApiModelProperty(value = "违法或违反监管具体情况")
	private String illegalDetail;

	/**
	 * 是否政治人物或高级政府官员是直系亲属
	 */
	@ApiModelProperty(value = "政治人物公职")
	private Boolean isHaveLinealRelatives;

	/**
	 * 政治人物名称
	 */
	@ApiModelProperty(value = "政治人物名称")
	private String linealRelativesName;

	/**
	 * 政治人物所担任的公职
	 */
	@ApiModelProperty(value = "政治人物公职")
	private String linealRelativesJob;

	/**
	 * 政治人物所担任公职期限
	 */
	@ApiModelProperty(value = "政治人物任职期限")
	private String linealRelativesJobTime;

	/**
	 * 与客户之关系
	 */
	@ApiModelProperty(value = "与客户之关系")
	private Integer customerRelations;

	/**
	 * 配偶是否在公司开有账户
	 */
	@ApiModelProperty(value = "配偶是否在公司开有账户")
	private Boolean isMateHaveAccount;

	/**
	 * 配偶账户
	 */
	@ApiModelProperty(value = "配偶账户")
	private String mateAccount;

	/**
	 * 是否个人或配偶共同控制iFund公司客户35%以上的股权
	 */
	@ApiModelProperty(value = "泰君安集团的公司客户35%以上的股权")
	private Boolean isStockRightMore35;

	/**
	 * 35%以上账户
	 */
	@ApiModelProperty(value = "35%以上账户")
	private String account;
}
