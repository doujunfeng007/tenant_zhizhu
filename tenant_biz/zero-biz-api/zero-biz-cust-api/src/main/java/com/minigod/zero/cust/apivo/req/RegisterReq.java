package com.minigod.zero.cust.apivo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: chen
 * @Project: zero
 * @Pcakage: com.minigod.zero.cust.apivo.req.RegisterReq
 * @Date: 2023年03月17日 17:07
 * @Description:
 */
@Data
public class RegisterReq implements Serializable {

	private static final long serialVersionUID = -1L;

	@ApiModelProperty(value = "区号")
	private String areaCode;

	@ApiModelProperty(value = "密码校验类型：1-用户号，2-手机号，3-邮箱")
	private Integer checkType;

	@ApiModelProperty(value = "当前注册用户的手机号(不带区号)")
	private String phoneNum;

	@ApiModelProperty(value = "用户号/邮箱")
	private String username;

	@ApiModelProperty(value = "登录密码")
	private String password;

	@ApiModelProperty(value = "请求源", required = true)
	private int SourceType;

	/**
	 * 交易账号
	 */
	private String tradeAccount;


	/**
	 * 租户id
	 */
	private String tenantId;

}
