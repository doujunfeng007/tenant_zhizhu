package com.minigod.zero.trade.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author:yanghu.luo
 * @create: 2023-05-24 20:40
 * @Description: 修改交易密码
 */
@Data
public class ModifyPwdVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 交易账号
	 */
	@ApiModelProperty(value = "交易账号")
	private String tradeAccount;

	/**
	 * 资金账号
	 */
	@ApiModelProperty(value = "资金账号")
	private String capitalAccount;

	/**
	 * 旧密码
	 */
	@ApiModelProperty(value = "旧密码")
	private String password;

	/**
	 * 新密码
	 */
	@ApiModelProperty(value = "新密码")
	private String newPassword;
}
