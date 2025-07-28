package com.minigod.zero.cust.apivo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: chen
 * @Project: zero
 * @Pcakage: com.minigod.zero.cust.apivo.req.CaptchaVO
 * @Date: 2023年03月03日 17:19
 * @Description:
 */
@Data
public class CaptchaVO implements Serializable {

	private static final long serialVersionUID = -1L;

	@ApiModelProperty(value = "手机号")
	private String phone;

	@ApiModelProperty(value = "区号")
	private String areaCode;



}
