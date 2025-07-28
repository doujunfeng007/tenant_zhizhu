package com.minigod.zero.cust.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 李长春
 * @email cloud@bdie.com.cn
 * @WeChat 138264386257
 * @date 2023/3/27
 */
@Data
@ApiModel(value = "2FA参数对象", description = "交易解锁参数对象")
public class Cust2faVO {

	/**
	 * 2FA验证场景（获取验证码参数：1-交易解锁 2-其他场景）
	 */
	@ApiModelProperty(value = "2FA验证场景（获取验证码参数：1-交易解锁 2-其他场景）")
	private Integer sceneType;

	/**
	 * 2FA验证码
	 */
	@ApiModelProperty(value = "2FA验证码")
	private String captcha;

	/**
	 * 验证码Key（获取验证码接口返回）
	 */
	@ApiModelProperty(value = "验证码Key（获取验证码接口返回）")
	private String captchaKey;

	/**
	 * 7天不用做2FA验证：0-否 1-是（交易解锁场景）
	 */
	@ApiModelProperty(value = "7天不用做2FA验证：0-否 1-是（交易解锁场景）")
	private Integer cancel2fa;

}

