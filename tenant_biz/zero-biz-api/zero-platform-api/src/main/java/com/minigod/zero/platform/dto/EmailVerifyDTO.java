package com.minigod.zero.platform.dto;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chen
 * @ClassName EmailverifyDTO.java
 * @Description TODO
 * @createTime 2024年10月12日 09:59:00
 */
@Data
public class EmailVerifyDTO {


	private String captchaCode;

	private String captchaKey;


	@ApiModelProperty("模板编码")
	private int code;

	private String tenantId;

	private List<String> accepts;


}
