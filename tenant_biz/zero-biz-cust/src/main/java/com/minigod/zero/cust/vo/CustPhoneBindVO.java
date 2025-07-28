package com.minigod.zero.cust.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author:yanghu.luo
 * @create: 2023-03-30 19:25
 * @Description: 手机号绑定请求对象
 */
@Data
@ApiModel(value = "手机号绑定请求对象", description = "手机号绑定请求对象")
public class CustPhoneBindVO {
	@ApiModelProperty(value = "手机号")
	private String phone;

	@ApiModelProperty(value = "区号")
	private String area;

	@ApiModelProperty(value = "验证码")
	private String captcha;

	@ApiModelProperty(value = "验证码Key（获取验证码接口返回）")
	private String captchaKey;
}
