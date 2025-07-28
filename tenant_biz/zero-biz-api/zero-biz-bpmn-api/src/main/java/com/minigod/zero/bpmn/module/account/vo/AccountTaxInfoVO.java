package com.minigod.zero.bpmn.module.account.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 开户资料个人税务信息返回对象
 *
 * @author eric
 * @since 2024-08-05 15:47:32
 */
@Data
@ApiModel(value = "AccountTaxInfoVO", description = "开户资料个人税务信息返回对象")
public class AccountTaxInfoVO {
	@ApiModelProperty(value = "税务信息集合")
	private List<AccountTaxationItemVO> accountTaxationItemVOs;

	@ApiModelProperty(value = "税务信息全量集合")
	private List<AccountTaxationInfoVO> accountTaxationInfoVOs;

	@ApiModelProperty(value = "W-8BEN表格美国税务局表")
	private AccountW8benInfoVO accountW8benInfoVO;
}
