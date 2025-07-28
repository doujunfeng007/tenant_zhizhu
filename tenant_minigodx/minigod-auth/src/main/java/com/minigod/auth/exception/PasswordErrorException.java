package com.minigod.auth.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/6/11 9:47
 * @description：
 */
public class PasswordErrorException extends AuthenticationException {
	public PasswordErrorException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public PasswordErrorException(String msg) {
		super(msg);
	}
}
