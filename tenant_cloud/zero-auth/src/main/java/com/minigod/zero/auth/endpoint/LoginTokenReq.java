package com.minigod.zero.auth.endpoint;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 未用到
 */
@Data
@ApiModel(value = "客户端登录参数", description = "客户端登录参数")
public class LoginTokenReq implements Serializable {

	@ApiModelProperty(value = "登录方式（register-手机号注册登录，trade-交易账号登录，trade2fa-交易身份认证）", required = true)
	private String grantType;

	@ApiModelProperty(value = "国际区号")
	private String area;

	@ApiModelProperty(value = "手机号")
	private String phone;

	@ApiModelProperty(value = "登录账号")
	private String account;

	@ApiModelProperty(value = "密码")
	private String password;

	@ApiModelProperty(value = "注册邀请人（预留）")
	private String inviter;

	@ApiModelProperty(value = "客户渠道（预留）")
	private String custChannel;

}
