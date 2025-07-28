package com.minigod.zero.cust.apivo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class FindCustByPhonesReq implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "手机号（带区号）")
	private List<String> phones;

	@ApiModelProperty(value = "租户ID")
	private String tenantId;
}
