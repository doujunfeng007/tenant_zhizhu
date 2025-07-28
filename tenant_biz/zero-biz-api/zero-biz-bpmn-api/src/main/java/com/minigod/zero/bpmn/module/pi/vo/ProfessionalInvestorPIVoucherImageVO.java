package com.minigod.zero.bpmn.module.pi.vo;

import com.minigod.zero.bpmn.module.pi.entity.ProfessionalInvestorPIVoucherImageEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 专业投资者FI申请凭证图片VO数据模型
 *
 * @author eric
 * @since 2024-05-07 14:56:09
 */
@Data
@ApiModel(value = "ProfessionalInvestorPIVoucherImageVO对象", description = "专业投资者FI申请凭证图片数据模型")
@EqualsAndHashCode(callSuper = true)
public class ProfessionalInvestorPIVoucherImageVO extends ProfessionalInvestorPIVoucherImageEntity {
}
