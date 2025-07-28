package com.minigod.zero.bpmn.module.account.bo.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
/**
 * 机构开户资料提交记录查询参数对象
 *
 * @author eric
 * @since 2024-05-31 16:41:09
 */
@Data
public class OrganizationOpenInfoQuery implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 开户方式
	 */
	@ApiModelProperty(value = "开户方式")
	private Integer openAccountAccessWay;

	/**
	 * 公司名称
	 */
	@ApiModelProperty(value = "公司名称")
	private String companyName;

	/**
	 * 公司地址
	 */
	@ApiModelProperty(value = "公司地址")
	private String companyAddress;

	/**
	 * 注册地
	 */
	@ApiModelProperty(value = "注册地")
	private String registrationLocation;

	/**
	 * 业务性质
	 */
	@ApiModelProperty(value = "业务性质")
	private String businessNature;

	/**
	 * 资金来源
	 */
	@ApiModelProperty(value = "资金来源")
	private String fundingSource;

	/**
	 * 联络人
	 */
	@ApiModelProperty(value = "联络人")
	private String contact;

	/**
	 * 联络人电话号码
	 */
	@ApiModelProperty(value = "联络人电话号码")
	private String contactPhoneNumber;

	/**
	 * 联络人邮箱
	 */
	@ApiModelProperty(value = "联络人邮箱")
	private String contactEmail;

	/**
	 * 开户用途
	 */
	@ApiModelProperty(value = "开户用途")
	private String purposeOpenAccount;

	/**
	 * 银行名称
	 */
	@ApiModelProperty(value = "银行名称")
	private String bankName;

	/**
	 * 银行代码
	 */
	@ApiModelProperty(value = "银行代码")
	private String swiftCode;

	/**
	 * 银行账户名称
	 */
	@ApiModelProperty(value = "银行账户名称")
	private String accountName;

	/**
	 * 银行账户号码
	 */
	@ApiModelProperty(value = "银行账户号码")
	private String accountNumber;

	/**
	 * 提交开户起始时间
	 */
	@ApiModelProperty(value = "提交开户起始时间")
	private String beginOpenDate;
	/**
	 * 提交开户截止时间
	 */
	@ApiModelProperty(value = "提交开户截止时间")
	private String endOpenDate;

	/**
	 * 审核起始时间
	 */
	@ApiModelProperty(value = "审核起始时间")
	private String beginApproveDate;
	/**
	 * 审核截止时间
	 */
	@ApiModelProperty(value = "审核截止时间")
	private String endApproveDate;

	/**
	 * 租户ID
	 */
	@ApiModelProperty(value = "租户ID")
	private String tenantId;
}
