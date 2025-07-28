package com.minigod.zero.platform.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author caizongtai
 * @since 2023/7/10 19:18
 */

@Data
@ApiModel("图像验证码返回对象")
public class GraphValidateCodeDto {

	@ApiModelProperty(value = "base64编码的图像url")
	private String baseCode;

	@ApiModelProperty(value = "图像验证码key")
	private String captchaKey;
}
