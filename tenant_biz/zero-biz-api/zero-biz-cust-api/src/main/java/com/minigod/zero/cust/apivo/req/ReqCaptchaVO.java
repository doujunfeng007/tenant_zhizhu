package com.minigod.zero.cust.apivo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author: chen
 * @Project: zero
 * @Pcakage: com.minigod.zero.cust.apivo.req.ReqCaptchaVO
 * @Date: 2023年03月13日 10:49
 * @Description:
 */
@Data
public class ReqCaptchaVO implements Serializable {

	private static final long serialVersionUID = -1L;

	@ApiModelProperty(value = "手机号")
	private String phoneNumber;

	@ApiModelProperty(value = "区号")
	private String areaCode;

	@ApiModelProperty(value = "验证码")
	private String captchaCode;


}
