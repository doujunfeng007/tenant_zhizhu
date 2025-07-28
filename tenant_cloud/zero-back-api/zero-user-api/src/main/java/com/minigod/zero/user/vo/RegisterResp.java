package com.minigod.zero.user.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: chen
 * @Project: zero
 * @Date: 2023年03月17日 17:07
 * @Description:
 */
@Data
public class RegisterResp implements Serializable {

	private static final long serialVersionUID = -1L;

	@ApiModelProperty(value = "邮箱")
	private String email;

	@ApiModelProperty(value = "手机号(不带区号)")
	private String phone;

	@ApiModelProperty(value = "智珠账号")
	private String account;

	@ApiModelProperty(value = "用户密码")
	private String password;

	@ApiModelProperty(value = "注册结果：0-失败 1-成功 2-已存在")
	private int result;

}
