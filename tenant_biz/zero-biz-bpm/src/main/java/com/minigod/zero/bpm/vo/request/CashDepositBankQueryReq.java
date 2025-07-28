package com.minigod.zero.bpm.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 区域入金银行列表 请求入参
 *
 * @author wengzejie
 * @since 2023-05-18
 */
@Data
public class CashDepositBankQueryReq {

	@ApiModelProperty(value = "银行名称")
	private String bankName;

	@ApiModelProperty(value = "银行类型 1美国 2香港 3大陆")
	private Integer bankType;
}
