package com.minigod.zero.platform.core;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/12/10 16:34
 * @description：
 */

public interface MessageSend {
	/**
	 * 普通消息
	 * @param message
	 * @return
	 */
	SendResult send(Message message);
	/**
	 * 验证码消息
	 * @param message
	 * @return
	 */
	SendResult sendVerificationCode(Message message);
	/**
	 * 校验验证码
	 * @param message
	 * @return
	 */
	SendResult checkVerificationCode(Message message);


	boolean supports(Class<?> authentication);
}
