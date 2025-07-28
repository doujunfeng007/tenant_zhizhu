package com.minigod.zero.bpmn.module.pi.bo.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 投资者申请记录查询参数对象
 *
 * @author eric
 * @since 2024-05-07 15:01:09
 */
@Data
public class ProfessionalInvestorPIApplicationQuery implements Serializable {
	/**
	 * 申请开始时间
	 */
	@ApiModelProperty(value = "申请开始时间")
	private String beginApplyDate;
	/**
	 * 申请结束时间
	 */
	@ApiModelProperty(value = "申请结束时间")
	private String endApplyDate;
	/**
	 * 认定开始日期
	 */
	@ApiModelProperty("认定开始日期")
	private String beginRecognitionDate;
	/**
	 * 认定结束日期
	 */
	@ApiModelProperty("认定结束日期")
	private String endRecognitionDate;
	/**
	 * 失效开始日期
	 */
	@ApiModelProperty("失效开始日期")
	private String beginExpirationDate;
	/**
	 * 失效结束日期
	 */
	@ApiModelProperty("失效结束日期")
	private String endExpirationDate;

	/**
	 * 撤销开始日期
	 */
	@ApiModelProperty("撤销开始日期")
	private String beginRevocationDate;
	/**
	 * 撤销结束日期
	 */
	@ApiModelProperty("撤销结束日期")
	private String endRevocationDate;
	/**
	 * 客户ID
	 */
	@ApiModelProperty(value = "客户ID")
	private String clientId;

	/**
	 * 客户账号
	 */
	@ApiModelProperty(value = "客户账号")
	private String clientAccount;

	/**
	 * 客户姓名
	 */
	@ApiModelProperty(value = "客户姓名")
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
	 * 流程审核状态
	 */
	@ApiModelProperty(value = "流程审核状态")
	private Integer applicationStatus;

	/**
	 * 流程审核状态
	 */
	@ApiModelProperty("状态列表")
	private List<Integer> applicationStatusList;

	/**
	 * 业务审核状态
	 */
	@ApiModelProperty(value = "业务审核状态")
	private Integer status;
	/**
	 * 租户ID
	 */
	@ApiModelProperty(value = "租户ID")
	private String tenantId;

}
