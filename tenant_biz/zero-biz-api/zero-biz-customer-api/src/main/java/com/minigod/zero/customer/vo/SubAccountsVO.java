package com.minigod.zero.customer.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/5/7 17:04
 * @description：
 */
@Data
public class SubAccountsVO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * subacctid 主键
	 */
	@ApiModelProperty(value = "主键")
	private Long subAcctId;

	/**
	 * 资金账户 subAccount
	 */
	@ApiModelProperty(value = "资金账户")
	private String subAccount;

	/**
	 * 账户类型[0=现金账户 M=保证金账户]
	 */
	@ApiModelProperty(value = "账户类型[0=现金账户 M=保证金账户]")
	private String subAccountType;

	/**
	 * 绑定状态，默认 1-已绑定
	 */
	@ApiModelProperty(value = "绑定状态，默认 1-已绑定")
	private  Integer status;
}
