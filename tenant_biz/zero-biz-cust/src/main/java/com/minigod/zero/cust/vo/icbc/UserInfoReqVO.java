package com.minigod.zero.cust.vo.icbc;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author:yanghu.luo
 * @create: 2023-03-08 09:36
 * @Description: 用户信息查询
 */
@Data
public class UserInfoReqVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 登录账号 工银 loginName
	 */
	@ApiModelProperty(value = "登录账号 工银 loginName")
	private String loginName;

	/**
	 * 设备号，设备UUID
	 */
	@ApiModelProperty(value = "设备号，设备UUID")
	private String deviceIdentity;
}
