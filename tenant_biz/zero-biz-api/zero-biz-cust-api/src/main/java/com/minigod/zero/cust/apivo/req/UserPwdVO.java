package com.minigod.zero.cust.apivo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: chen
 * @Project: zero
 * @Pcakage: com.minigod.zero.cust.apivo.req.UserPwdVO
 * @Date: 2023年02月18日 15:27
 * @Description:
 */
@Data
public class UserPwdVO implements Serializable {

	private static final long serialVersionUID = -1L;


	@ApiModelProperty(value = "事件ID(验证码返回的id)")
	private Integer eventId;

	@ApiModelProperty(value = "解密码的key")
	private String key;

	@ApiModelProperty(value = "用户密码")
	private String pwd;

	@ApiModelProperty(value = "手机")
	private String phoneNum;

	@ApiModelProperty(value = "手机号区号")
	private String areaCode;

	@ApiModelProperty(value = "邮箱")
	private String email;

	@ApiModelProperty(value = "验证码")
	private String captcha;


}
