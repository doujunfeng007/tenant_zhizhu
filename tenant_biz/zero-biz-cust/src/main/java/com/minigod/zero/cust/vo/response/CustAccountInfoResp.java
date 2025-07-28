package com.minigod.zero.cust.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CustAccountInfoResp implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "账号类型：1-个人 2-联名 3-公司")
	private String acctType;

	@ApiModelProperty(value = "交易账号")
	private String tradeAccount;

	@ApiModelProperty(value = "资金账号列表")
	private List<CustCapitalInfoResp> capitalAccounts;

}
