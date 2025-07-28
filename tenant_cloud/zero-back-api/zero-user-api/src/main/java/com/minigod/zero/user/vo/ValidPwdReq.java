package com.minigod.zero.user.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户密码校验
 */
@Data
public class ValidPwdReq implements Serializable {
	private static final long serialVersionUID = 5515007953715314583L;

	@ApiModelProperty(value = "邮箱")
	@NotNull(message = "邮箱不能为空")
	private String email;

	@ApiModelProperty(value = "密码")
	@NotNull(message = "密码不能为空")
	private String password;

	private Integer userType;
}
