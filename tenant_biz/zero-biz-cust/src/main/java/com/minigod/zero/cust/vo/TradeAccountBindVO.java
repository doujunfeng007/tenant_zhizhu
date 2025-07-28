package com.minigod.zero.cust.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author:yanghu.luo
 * @create: 2023-03-31 11:49
 * @Description: 交易账号绑定
 */
@Data
@ApiModel(value = "交易账号绑定对象", description = "交易账号绑定对象")
public class TradeAccountBindVO{
	@ApiModelProperty(value = "交易账号", required = true)
	private String account;

	@ApiModelProperty(value = "交易账号状态", required = true)
	private String status;

	@ApiModelProperty(value = "手机号")
	private String phone;

	@ApiModelProperty(value = "区号")
	private String area;

	@ApiModelProperty(value = "验证码")
	private String captcha;

	@ApiModelProperty(value = "验证码Key（获取验证码接口返回）")
	private String captchaKey;
}
