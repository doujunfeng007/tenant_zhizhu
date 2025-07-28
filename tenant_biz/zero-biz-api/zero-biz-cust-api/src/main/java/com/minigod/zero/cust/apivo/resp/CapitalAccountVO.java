package com.minigod.zero.cust.apivo.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author:yanghu.luo
 * @create: 2023-05-18 11:05
 * @Description: 资金账户
 */
@Data
public class CapitalAccountVO implements Serializable {
	private static final long serialVersionUID = -1L;

	/**
	 * 资金账号
	 */
	@ApiModelProperty(value = "资金账号")
	private String capitalAccount;
	/**
	 * 账户类型：[0=现金账户 M=保证金账户]
	 */
	@ApiModelProperty(value = "账户类型：[0=现金账户 M=保证金账户]")
	private String accountType;
}
