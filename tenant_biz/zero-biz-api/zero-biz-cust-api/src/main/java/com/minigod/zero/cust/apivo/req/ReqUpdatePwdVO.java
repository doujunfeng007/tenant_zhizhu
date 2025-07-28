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
public class ReqUpdatePwdVO  implements Serializable {

	private static final long serialVersionUID = 5515007953715314583L;

	@ApiModelProperty(value = "客户旧密码" ,required = true)
	private String oldPassword;

	@ApiModelProperty(value = "客户新密码" ,required = true)
	private String newPassword;

	@ApiModelProperty(value = "验证码" ,required = true)
	private String captchaCode;

	@ApiModelProperty(value = "短信验证码Key，获取验证码时后端返回" ,required = true)
	private String captchaKey;

	@ApiModelProperty(value = "区号" ,required = true)
	private String areaCode;

	@ApiModelProperty(value = "手机号" ,required = true)
	private String phone;

	@ApiModelProperty(value = "邮箱" ,required = true)
	private String email;

	@ApiModelProperty(value = "客户类型" ,required = true)
	private Integer custType;

}
