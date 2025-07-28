package com.minigod.zero.bpmn.module.account.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 机构开户申请提交表
 *
 * @TableName organization_open_info
 */
@Data
@TableName("organization_open_info")
@ApiModel(value = "OrganizationOpenInfo对象", description = "机构开户申请提交表对象")
public class OrganizationOpenInfoEntity extends TenantEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	@ApiModelProperty(value = "用户ID")
	private Long custId;
	/**
	 * 开户方式
	 */
	@ApiModelProperty(value = "开户方式")
	private Integer openAccountAccessWay;

	/**
	 * 客户号(交易帐号)
	 */
	@ApiModelProperty(value = "客户号(交易帐号)")
	private String tradeAccount;

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
	 * 公司注册证书(链接)
	 */
	@ApiModelProperty(value = "公司注册证书(链接)")
	private String companyRegistCertUrl;

	/**
	 * 商业登记证书(链接)
	 */
	@ApiModelProperty(value = "商业登记证书(链接)")
	private String businessRegistCertUrl;

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
	 * 联络人电话区号
	 */
	@ApiModelProperty(value = "联络人电话区号")
	private String contactPhoneArea;

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
	 * 风险承受程度:[6=非常进取型 5=进取型 4=中度进取型 3=平稳型 2=中度保守型 1=保守型]
	 */
	@ApiModelProperty(value = "风险承受程度:[6=非常进取型 5=进取型 4=中度进取型 3=平稳型 2=中度保守型 1=保守型]")
	private Integer acceptRisk;

	/**
	 * 失效日期
	 */
	@ApiModelProperty(value = "失效日期")
	private Date expiryDate;

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
	 * 审核状态:0.待审核 1.审核通过 2.审核不通过
	 */
	@ApiModelProperty(value = "审核状态:0.待审核 1.审核通过 2.审核不通过")
	private Integer approveStatus;

	/**
	 * 审核意见
	 */
	@ApiModelProperty(value = "审核意见")
	private String approveReason;

	/**
	 * 审核人
	 */
	@ApiModelProperty(value = "审核人ID")
	private Long approveUserId;

	/**
	 * 审核人名称
	 */
	@ApiModelProperty(value = "审核人名称")
	private String approveUserName;

	/**
	 * 审核日期
	 */
	@ApiModelProperty(value = "审核日期")
	private Date approveDate;

	/**
	 * 开户日期
	 */
	@ApiModelProperty(value = "开户日期")
	private Date openDate;

	/**
	 * 开户状态:0.待开户 1.开户成功 2.开户失败
	 */
	@ApiModelProperty(value = "开户状态:0.待开户 1.开户成功 2.开户失败")
	private Integer openStatus;

	/**
	 * 开户结果
	 */
	@ApiModelProperty(value = "开户结果")
	private String openResult;
}
