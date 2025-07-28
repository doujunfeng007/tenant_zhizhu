package com.minigod.zero.platform.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 李长春
 * @email cloud@bdie.com.cn
 * @WeChat 138264386257
 * @date 2023/3/14
 */
@Data
@ApiModel(value = "短信验证码对象", description = "短信验证码对象")
public class SmsCaptchaVO implements Serializable {

	@ApiModelProperty(value = "区号（不带符号）")
	private String area;

	@ApiModelProperty(value = "手机号（不带区号）")
	private String phone;

	@ApiModelProperty(value = "电子邮箱")
	private String email;

	@ApiModelProperty(value = "验证码（校验必传）")
	private String captcha;

	@ApiModelProperty(value = "验证码Key（获取验证码接口返回，校验必传）")
	private String captchaKey;

	@ApiModelProperty(value = "语言")
	private String lang;

	@ApiModelProperty(hidden = true)
	private long time;

	@ApiModelProperty(value = "模板编码：3006-智珠绑定邮箱 2373-智珠重置密码 3008-ESOP找回密码")
	private int code;

}
