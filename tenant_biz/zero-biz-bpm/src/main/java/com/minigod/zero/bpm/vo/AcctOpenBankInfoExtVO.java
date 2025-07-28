package com.minigod.zero.bpm.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 客户银行扩展信息 视图实体类
 *
 * @author wengzejie
 * @since 2023-05-18
 */
@Data
public class AcctOpenBankInfoExtVO {

	@ApiModelProperty(value = "是否已开户")
	private boolean show;

	@ApiModelProperty(value = "客户银行信息")
	private AcctOpenBankInfoVO acctBankInfo;

}
