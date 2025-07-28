package com.minigod.zero.cust.apivo.req;

import lombok.Data;

import java.io.Serializable;

/**
 * 校验用户交易密码
 */
@Data
public class CheckClientPwdReq implements Serializable {

	private static final long serialVersionUID = 5515007953715314583L;

	/**
	 * 新密码
	 */
	private String newPassword;
	/**
	 * 客户ID
	 */
	private Long custId;
}

