package com.minigod.zero.bpm.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 其他银行卡协议 请求入参
 *
 * @author wengzejie
 * @since 2023-05-18
 */
@Data
public class OtherBankInfoQueryReq {

	@ApiModelProperty(value = "银行名称")
	private String bankName;

	@ApiModelProperty(value = "银行国际编号")
	private String swiftCode;

	@ApiModelProperty(value = "是否入金银行 0 出入金 1 出金 2其他")
	private Integer isAmount;
}
