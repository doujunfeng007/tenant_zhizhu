package com.minigod.zero.bpmn.module.account.vo;

import com.minigod.zero.bpmn.module.account.entity.AccountTaxationInfoModifyEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 开户资料个人税务信息修改返回对象
 *
 * @author eric
 * @since 2024-08-05 15:47:32
 */
@Data
@ApiModel(value = "AccountTaxInfoModifyVO", description = "开户资料个人税务信息修改返回对象")
public class AccountTaxInfoModifyVO {
	@ApiModelProperty(value = "税务信息修改")
	private List<AccountTaxationInfoModifyVO> accountTaxationInfoModifyVos;

	@ApiModelProperty(value = "W-8BEN表格美国税务局表修改")
	private AccountW8benInfoModifyVO accountW8benInfoModifyVO;
}
