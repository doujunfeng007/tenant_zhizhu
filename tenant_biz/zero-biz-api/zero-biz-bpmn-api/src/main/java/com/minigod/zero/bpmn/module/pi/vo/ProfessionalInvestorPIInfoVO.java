package com.minigod.zero.bpmn.module.pi.vo;

import com.minigod.zero.bpmn.module.pi.entity.ProfessionalInvestorPIInfoEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 专业投资者FI申请基本信息VO数据模型
 *
 * @author eric
 * @since 2024-05-07 14:56:09
 */
@Data
@ApiModel(value = "ProfessionalInvestorPIInfoVO对象", description = "专业投资者FI申请基本信息数据模型")
@EqualsAndHashCode(callSuper = true)
public class ProfessionalInvestorPIInfoVO extends ProfessionalInvestorPIInfoEntity {
	@ApiModelProperty(value = "手机号码+区号")
	private String securitiesPhoneAreaNumber;
}
