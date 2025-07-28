package com.minigod.zero.cust.apivo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: chen
 * @Project: zero
 * @Pcakage: com.minigod.zero.cust.apivo.req.ReqSetPwdVO
 * @Date: 2023年02月18日 15:07
 * @Description:
 */
@Data
public class ReqSetPwdVO implements Serializable {

	private static final long serialVersionUID = -1L;

	@ApiModelProperty(value = "邮箱")
	private String email;

	@ApiModelProperty(value = "密码")
	private String pwd;

	@ApiModelProperty(value = "解密码的key")
	private String key;




}
