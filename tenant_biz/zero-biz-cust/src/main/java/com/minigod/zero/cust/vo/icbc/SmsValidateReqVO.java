package com.minigod.zero.cust.vo.icbc;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author:yanghu.luo
 * @create: 2023-03-08 09:32
 * @Description: 短信验证码验证
 */
@Data
public class SmsValidateReqVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 验证码key,后台用于保存验证码值的key，发送验证码的时候传给前端
	 */
	@ApiModelProperty(value = "验证码key,后台用于保存验证码值的key，发送验证码的时候传给前端")
	private String captchaKey;

	/**
	 * 用户输入的验证码
	 */
	@ApiModelProperty(value = "用户输入的验证码")
	private String captchaInput;
}
