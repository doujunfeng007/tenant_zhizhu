package com.minigod.auth.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/6/11 9:47
 * @description：密码错误次数超过最大异常
 */
public class PasswordErrorThanMaxException extends AuthenticationException {
	public PasswordErrorThanMaxException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public PasswordErrorThanMaxException(String msg) {
		super(msg);
	}
}
