package com.minigod.zero.biz.common.exception;

/**
 * @author huangwei
 * @version 1.0
 * @description TODO
 * @mail h549866023@qq.com
 * @date 2022/11/21 17:59
 **/
public class ZeroException extends RuntimeException {
	private static final long serialVersionUID = -6830494624999358786L;
	public ZeroException() {
	}

	public ZeroException(String message, Throwable cause) {
		super(message, cause);
	}

	public ZeroException(String message) {
		super(message);
	}

	public ZeroException(Throwable cause) {
		super(cause);
	}
}
