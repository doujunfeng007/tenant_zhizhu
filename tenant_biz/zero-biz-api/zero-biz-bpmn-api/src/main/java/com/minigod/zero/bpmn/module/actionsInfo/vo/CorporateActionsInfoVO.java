package com.minigod.zero.bpmn.module.actionsInfo.vo;

import com.minigod.zero.bpmn.module.actionsInfo.entity.CorporateActionsInfoEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 公司行动记录表 视图实体类
 *
 * @author wengzejie
 * @since 2024-03-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "公司行动记录VO对象", description = "公司行动记录表")
public class CorporateActionsInfoVO extends CorporateActionsInfoEntity {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "状态名称")
	private String statusName;
}
