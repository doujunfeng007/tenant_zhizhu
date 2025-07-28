package com.minigod.zero.platform.core;

import lombok.Data;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/12/12 10:55
 * @description：
 */
@Data
public class VerificationCodeMessage extends Message {
	private String captchaKey;
	private String captchaCode;
	private Long expire;
}
