package com.minigod.zero.cust.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author:yanghu.luo
 * @create: 2023-03-30 19:25
 * @Description: 绑定开户手机号到登陆手机号
 */
@Data
@ApiModel(value = "绑定开户手机号到登陆手机号", description = "绑定开户手机号到登陆手机号")
public class CustPhoneBindOpenVO {
	@ApiModelProperty(value = "手机号")
	private String phone;

	@ApiModelProperty(value = "区号")
	private String area;

	@ApiModelProperty(value = "交易密码（RSA）")
	private String password;
}
