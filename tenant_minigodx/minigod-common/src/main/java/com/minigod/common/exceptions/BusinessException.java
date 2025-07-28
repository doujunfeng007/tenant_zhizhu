package com.minigod.common.exceptions;

import java.io.Serializable;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/4/18 14:41
 * @description：
 */
public class BusinessException extends RuntimeException implements Serializable {
	private final IResultCode resultCode;

	public BusinessException(String message) {
		super(message);
		this.resultCode = ResultCode.FAILURE;
	}

	public BusinessException(IResultCode resultCode) {
		super(resultCode.getMessage());
		this.resultCode = resultCode;
	}

	public BusinessException(IResultCode resultCode, Throwable cause) {
		super(cause);
		this.resultCode = resultCode;
	}

	public Throwable fillInStackTrace() {
		return this;
	}

	public Throwable doFillInStackTrace() {
		return super.fillInStackTrace();
	}

	public IResultCode getResultCode() {
		return this.resultCode;
	}
}
