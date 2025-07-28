package com.minigod.zero.bpmn.module.pi.dto;

import com.minigod.zero.bpmn.module.pi.entity.ProfessionalInvestorPIApplicationEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 专业投资者申请表 数据传输对象实体类
 *
 * @author eric
 * @since 2024-05-07 15:00:00
 */
@Data
@ApiModel(value = "ProfessionalInvestorPIApplicationDTO对象", description = "专业投资者FI申请数据传输对象实体类")
@EqualsAndHashCode(callSuper = true)
public class ProfessionalInvestorPIApplicationDTO extends ProfessionalInvestorPIApplicationEntity {
}
