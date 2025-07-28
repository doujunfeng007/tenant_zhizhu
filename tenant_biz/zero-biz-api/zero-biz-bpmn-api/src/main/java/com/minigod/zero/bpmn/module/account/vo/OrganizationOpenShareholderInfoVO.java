package com.minigod.zero.bpmn.module.account.vo;

import com.minigod.zero.bpmn.module.account.entity.OrganizationOpenShareholderInfoEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 机构开户股东信息
 *
 * @author eric
 * @since 2024-07-16 13:19:00
 */
@Data
public class OrganizationOpenShareholderInfoVO extends OrganizationOpenShareholderInfoEntity {
	@ApiModelProperty(value = "类型名称:1.先生 2.小姐 3.博士 4.公司")
	private String typeName;

	@ApiModelProperty(value = "职位:1.董事 2.股东 3.授权签署")
	private String titleName;

	@ApiModelProperty(value = "风险等级:1.低风险 2.中风险 3.高风险(PEP)")
	private String riskName;
}
