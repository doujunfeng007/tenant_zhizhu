package com.minigod.zero.bpmn.module.pi.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 专业投资者FI申请基本信息 实体类
 *
 * @author eric
 * @since 2024-05-07 13:59:20
 */
@Data
@TableName("professional_investor_pi_info")
@ApiModel(value = "ProfessionalInvestorFIInfoApplication对象", description = "专业投资者FI申请基本信息")
public class ProfessionalInvestorPIInfoEntity extends TenantEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 流水号
	 */
	@ApiModelProperty(value = "流水号")
	private String applicationId;
	/**
	 * 客户ID
	 */
	@ApiModelProperty(value = "客户ID")
	private Long clientId;

	/**
	 * 客户账号
	 */
	@ApiModelProperty(value = "客户账号")
	private String clientAccount;

	/**
	 * 客户名称
	 */
	@ApiModelProperty(value = "客户名称")
	private String clientName;

	/**
	 * 客户英文名称
	 */
	@ApiModelProperty(value = "客户英文名称")
	private String clientEngName;

	/**
	 * 证券手机号码区号
	 */
	@ApiModelProperty(value = "证券手机号码区号")
	private String securitiesPhoneArea;

	/**
	 * 证券手机号码
	 */
	@ApiModelProperty(value = "证券手机号码")
	private String securitiesPhoneNumber;
	/**
	 * 性别[0=男 1=女 2=其它]
	 */
	@ApiModelProperty(value = "性别[0=男 1=女 2=其它]")
	private Integer gender;
	/**
	 * 租户ID
	 */
	@TableField(value = "tenant_id")
	@ApiModelProperty(value = "租户ID")
	@Size(max = 20, message = "租户ID最大长度要小于 20")
	private String tenantId;

	/**
	 * 申请日期
	 */
	@DateTimeFormat(
		pattern = "yyyy-MM-dd"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd"
	)
	@ApiModelProperty("申请日期")
	private Date applyDate;

	/**
	 * 认定日期
	 */
	@DateTimeFormat(
		pattern = "yyyy-MM-dd"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd"
	)
	@ApiModelProperty("认定日期")
	private Date recognitionDate;

	/**
	 * 失效日期
	 */
	@DateTimeFormat(
		pattern = "yyyy-MM-dd"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd"
	)
	@ApiModelProperty("失效日期")
	private Date expirationDate;

	/**
	 * 撤销日期
	 */
	@DateTimeFormat(
		pattern = "yyyy-MM-dd"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd"
	)
	@ApiModelProperty("撤销日期")
	private Date revocationDate;

	/**
	 * 撤销原因
	 */
	@ApiModelProperty(value = "撤销原因")
	private String revocationReason;

	/**
	 * 流程发起时间
	 */
	@ApiModelProperty(value = "流程发起时间")
	private Date applicationTime;

	/**
	 * 专业投资者之待遇
	 */
	@ApiModelProperty(value = "专业投资者之待遇")
	private String treatment;

	/**
	 * 专业投资者之被视为专业投资者身份的确认书声明(勾选项)
	 */
	@ApiModelProperty(value = "专业投资者之被视为专业投资者身份的确认书声明(勾选项)")
	private String declaration;
	/**
	 * 续期日期
	 */
/*	@ApiModelProperty(value = "续期日期")
	private Date renewalDate;*/
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 账户类型，0个人户，1机构户
	 */
	private Integer accountType;
}
