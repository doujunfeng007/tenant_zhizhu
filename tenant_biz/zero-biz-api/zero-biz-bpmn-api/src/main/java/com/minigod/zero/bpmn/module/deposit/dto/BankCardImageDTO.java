package com.minigod.zero.bpmn.module.deposit.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 银行卡凭证信息 数据传输对象实体类
 *
 * @author eric
 * @since 2024-09-14 10:21:45
 */
@Data
public class BankCardImageDTO implements Serializable {
	/**
	 * 凭证类型
	 */
	@ApiModelProperty(value = "凭证类型[0-银行卡 1-银行凭证]")
	@NotNull(message = "凭证类型[0-银行卡 1-银行凭证]不能为空")
	private Integer type;

	/**
	 * 图片Base64码
	 */
	@ApiModelProperty(value = "图片Base64码")
	@NotBlank(message = "图片Base64码不能为空")
	private String imgBase64;
}
