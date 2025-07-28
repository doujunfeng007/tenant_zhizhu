package com.minigod.zero.customer.dto;

import com.minigod.zero.customer.entity.CustomerInfoEntity;
import io.protostuff.Message;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * 客户信息表 数据传输对象实体类
 *
 * @author ken
 * @since 2024-04-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerInfoDTO extends CustomerInfoEntity {
	private static final long serialVersionUID = 1L;
	/**
	 * 验证码的key
	 */
	@ApiModelProperty(value = "验证码的key")
	private String captchaKey;
	/**
	 * 验证码
	 */
	@ApiModelProperty(value = "验证码")
	private String captchaCode;
}
