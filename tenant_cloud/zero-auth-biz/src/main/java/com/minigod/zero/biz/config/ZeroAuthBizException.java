package com.minigod.zero.biz.config;

/**
 * @author:yanghu.luo
 * @create: 2023-08-24 18:54
 * @Description: 登陆异常类
 */
public class ZeroAuthBizException extends RuntimeException {

	private int code;
	private String message;

	public ZeroAuthBizException(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
