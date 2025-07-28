package com.minigod.zero.bpmn.module.account.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 *  身份确认信息
 *
 * @author Chill
 */
@Data
@TableName("open_account_identity_confirm")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AccountIdentityConfirm对象", description = "身份确认信息表")
public class AccountIdentityConfirmEntity extends TenantEntity {

	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private String applicationId;
	/**
	 * 是否公司雇员
	 */
	@ApiModelProperty(value = "是否公司雇员")
	private Boolean isEmployed;
	/**
	 * 违法或违反监管
	 */
	@ApiModelProperty(value = "违法或违反监管")
	private Boolean isIllegal;
	/**
	 * 是否公司的高级管理人员或董事
	 */
	@ApiModelProperty(value = "是否公司的高级管理人员或董事")
	private Boolean isExecutive;
	/**
	 * 亲属关系
	 */
	@ApiModelProperty(value = "亲属关系")
	private String employedRelation;
	/**
	 * 违法或违反监管具体情况
	 */
	@ApiModelProperty(value = "违法或违反监管具体情况")
	private String illegalDetail;
	/**
	 * 是否政治人物或高级政府官员是直系亲属
	 */
	@ApiModelProperty(value = "是否政治人物或高级政府官员是直系亲属")
	private Boolean isHaveLinealRelatives;
	/**
	 * 政治人物任职期限
	 */
	@ApiModelProperty(value = "政治人物任职期限")
	private String linealRelativesJobTime;
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
	 * 政治人物名称
	 */
	@ApiModelProperty(value = "政治人物名称")
	private String linealRelativesName;
	/**
	 * 股票代码
	 */
	@ApiModelProperty(value = "股票代码")
	private String executiveCompanyCode;
	/**
	 * 亲属姓名
	 */
	@ApiModelProperty(value = "亲属姓名")
	private String employedName;
	/**
	 * 与客户之关系
	 */
	@ApiModelProperty(value = "与客户之关系")
	private Integer customerRelations;
	/**
	 * 公司名称
	 */
	@ApiModelProperty(value = "公司名称")
	private String executiveCompanyName;
	/**
	 * 泰君安集团的公司客户35%以上的股权
	 */
	@ApiModelProperty(value = "泰君安集团的公司客户35%以上的股权")
	private Boolean isStockRightMore35;
	/**
	 * 是否证监会持牌或注册人士的雇员
	 */
	@ApiModelProperty(value = "是否证监会持牌或注册人士的雇员")
	private Boolean isRegisterEmployee;

	/**
	 * 您是否此帐户的最终实益拥有人/最终受益于交易及承担风险人士?
	 */
	@ApiModelProperty(value = "您是否此帐户的最终实益拥有人/最终受益于交易及承担风险人士?")
	private Boolean isBeneficialOwner;

	/**
	 * 您是否向户口最终负责发出指示的人士?
	 */
	@ApiModelProperty(value = "您是否向户口最终负责发出指示的人士?")
	private Boolean isAccountUltimatelyResponsible;
	/**
	 * 政治人物公职
	 */
	@ApiModelProperty(value = "政治人物公职")
	private String linealRelativesJob;
	/**
	 * 中央编号
	 */
	private String centerCode;
	/**
	 * 持牌发团或注册机构名称
	 */
	private String organizationName;
	/**
	 * 35%以上账户
	 */
	@ApiModelProperty(value = "35%以上账户")
	private String account;

}
