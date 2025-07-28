package com.minigod.zero.bpmn.module.pi.vo;

import com.minigod.zero.bpmn.module.pi.entity.ProfessionalInvestorPIApplicationEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 专业投资者FI申请记录VO数据模型
 *
 * @author eric
 * @since 2024-05-07 14:56:09
 */
@Data
@ApiModel(value = "ProfessionalInvestorPIApplicationVO对象", description = "专业投资者FI申请记录数据模型")
@EqualsAndHashCode(callSuper = true)
public class ProfessionalInvestorPIApplicationVO extends ProfessionalInvestorPIApplicationEntity {
	@ApiModelProperty(value = "状态名称")
	private String statusName;

	@ApiModelProperty(value = "申请状态名称")
	private String applicationStatusName;
	/**
	 * 申请记录基本信息
	 */
	@ApiModelProperty(value = "申请记录基本信息")
	public ProfessionalInvestorPIInfoVO info;
	/**
	 * 申请记录凭证图片
	 */
	@ApiModelProperty(value = "申请记录凭证图片信息")
	private List<ProfessionalInvestorPIVoucherImageVO> imagesList;
}
