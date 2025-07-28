package com.minigod.zero.trade.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 交易密码重置
 */
@Data
@ApiModel(value = "交易密码重置", description = "交易密码重置")
public class ResetTradePwdVO implements Serializable {
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
	 * 新密码
	 */
	@ApiModelProperty(value = "新密码")
	private String newPassword;

	/**
	 * esop邮箱重置交易密码
	 */
	@ApiModelProperty(value = "邮箱" ,required = true)
	private String email;
	@ApiModelProperty(value = "验证码" ,required = true)
	private String captchaCode;
	@ApiModelProperty(value = "短信验证码Key，获取验证码时后端返回" ,required = true)
	private String captchaKey;

	@ApiModelProperty(value = "证件号")
	private String idNo;

}

