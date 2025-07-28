package com.minigod.auth.exception;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/6/4 13:36
 * @description：
 */
public class PasswordMissingException extends RuntimeException {

	public PasswordMissingException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public PasswordMissingException(String msg) {
		super(msg);
	}
}
