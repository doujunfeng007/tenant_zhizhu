package com.minigod.zero.customer.dto;

import lombok.Data;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/7/2 16:40
 * @description：
 */
@Data
public class BiometricDTO {
	/**
	 * 设备号
	 */
	private String opStation;
	/**
	 * 1 指纹， 2 面容
	 */
	private Integer type;
	/**
	 * 交易密码
	 */
	private String password;
	/**
	 * 解锁token
	 */
	private String token;
}
