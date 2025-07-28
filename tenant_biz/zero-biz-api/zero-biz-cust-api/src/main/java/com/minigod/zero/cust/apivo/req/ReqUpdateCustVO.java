package com.minigod.zero.cust.apivo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: chen
 * @Project: zero
 * @Pcakage: com.minigod.zero.cust.apivo.req.ReqUpdatePwdVO
 * @Date: 2023年02月18日 14:32
 * @Description:
 */
@Data
public class ReqUpdateCustVO implements Serializable {

	private static final long serialVersionUID = 9153987577680898399L;

	@ApiModelProperty(value = "区号")
	private String areaCode;

	@ApiModelProperty(value = "手机号")
	private String phone;

	@ApiModelProperty(value = "邮箱")
	private String email;

	@ApiModelProperty(value = "验证码")
	private String captchaCode;

	@ApiModelProperty(value = "短信验证码Key，获取验证码时后端返回")
	private String captchaKey;

	@ApiModelProperty(value = "默认语言: zh-hans（简体）zh-hant（繁体）en（英文）")
	private String lang;

	@ApiModelProperty(value = "交易账号")
	private String tradeAccount;

	@ApiModelProperty(value = "资金账号")
	private String capitalAccount;

	@ApiModelProperty(value = "用于注销的密码")
	private String password;

}
