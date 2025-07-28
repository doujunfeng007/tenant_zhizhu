package com.minigod.zero.cust.apivo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: chen
 * @Project: zero
 * @Pcakage: com.minigod.zero.cust.apivo.req.NickNameReqVO
 * @Date: 2023年02月18日 18:27
 * @Description:
 */
@Data
public class NickNameVO implements Serializable {

	private static final long serialVersionUID = -1L;

	@ApiModelProperty(value = "用户昵称")
	private String nickName;

	@ApiModelProperty(value = "用户签名")
	private String signature;

	@ApiModelProperty(value = "用户性别(1男，0女)")
	private Integer gender;
}
