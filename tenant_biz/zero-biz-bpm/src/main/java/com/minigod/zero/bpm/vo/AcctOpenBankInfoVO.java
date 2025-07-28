package com.minigod.zero.bpm.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 客户银行信息 视图实体类
 *
 * @author wengzejie
 * @since 2023-05-18
 */
@Data
public class AcctOpenBankInfoVO {

	@ApiModelProperty(value = "银行名称")
	private String bankNo;

	@ApiModelProperty(value = "银行账号")
	private String bankAccount;
}
