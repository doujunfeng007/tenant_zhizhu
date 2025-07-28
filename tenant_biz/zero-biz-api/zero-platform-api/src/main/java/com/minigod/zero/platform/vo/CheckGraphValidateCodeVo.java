package com.minigod.zero.platform.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 掌上智珠
 * @since 2023/7/10 20:48
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckGraphValidateCodeVo {

	@ApiModelProperty(value = "验证key")
	private String captchaKey;

	@ApiModelProperty(value = "验证码")
	private String code;
}
