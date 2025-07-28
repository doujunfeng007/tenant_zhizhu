package com.minigod.zero.bpmn.module.account.dto;

import com.minigod.zero.core.mp.support.Query;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分页查询对象
 */
@Data
@ApiModel(value = "AccountOpenInfoModifyApplyPageDTO", description = "分页查询对象")
@EqualsAndHashCode(callSuper = true)
public class AccountOpenInfoModifyApplyPageDTO extends Query {
	/**
	 * 客户中文名
	 */
	@ApiModelProperty(value = "客户中文名")
	private String clientName;

	/**
	 * 客户英文名
	 */
	@ApiModelProperty(value = "客户英文名")
	private String clientNameSpell;
}
