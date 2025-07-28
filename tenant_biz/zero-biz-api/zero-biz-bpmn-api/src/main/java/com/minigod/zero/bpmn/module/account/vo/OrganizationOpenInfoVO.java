package com.minigod.zero.bpmn.module.account.vo;

import com.minigod.zero.bpmn.module.account.entity.OrganizationOpenInfoEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 组织机构开户信息
 *
 * @author eric
 * @since 2024-05-31 14:56:01
 */
@Data
public class OrganizationOpenInfoVO extends OrganizationOpenInfoEntity {
	/**
	 * 开户方式名称
	 */
	@ApiModelProperty(value = "开户方式名称")
	private String openAccountAccessWayName;

	@ApiModelProperty(value = "客户号")
	private String custIdStr;

	/**
	 * 风险承受程度:[6=非常进取型 5=进取型 4=中度进取型 3=平稳型 2=中度保守型 1=保守型]
	 */
	@ApiModelProperty(value = "风险承受程度:[6=非常进取型 5=进取型 4=中度进取型 3=平稳型 2=中度保守型 1=保守型]")
	private String acceptRiskName;

	@ApiModelProperty(value = "开户状态:0.待开户 1.开户成功 2.开户失败")
	private String openStatusName;

	@ApiModelProperty(value = "审核状态:0.待审核 1.审核通过 2.审核不通过")
	private String approveStatusName;

	@ApiModelProperty(value = "联络人电话区号")
	private String contactPhoneAreaNumber;

	/**
	 * 股东信息列表
	 */
	@ApiModelProperty(value = "股东信息列表")
	private List<OrganizationOpenShareholderInfoVO> shareholderInfoList;
}
