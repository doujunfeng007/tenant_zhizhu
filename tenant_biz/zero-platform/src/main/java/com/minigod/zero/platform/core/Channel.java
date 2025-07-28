package com.minigod.zero.platform.core;

import com.minigod.zero.platform.entity.PlatformMessageProperties;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/12/10 16:56
 * @description：
 */
public interface Channel {
	/**
	 * 发送消息
	 * @param message
	 * @return
	 */
	SendResult send(Message message);

	/**
	 * 第三方平台配置信息
	 * @param tenantId
	 * @return
	 */
	PlatformMessageProperties messageProperties(String tenantId);
}
