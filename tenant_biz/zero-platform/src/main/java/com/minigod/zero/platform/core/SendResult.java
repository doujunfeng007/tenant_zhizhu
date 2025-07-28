package com.minigod.zero.platform.core;

import lombok.Data;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/12/10 16:35
 * @description：
 */
@Data
public class SendResult {
	/**
	 * 消息类型
	 */
	private MessageType messageType;
	/**
	 * 本地消息id
	 */
	private String msgId;
	/**
	 * 第三方消息id
	 */
	private String sid;
	/**
	 * 发送状态
	 */
	private Integer status;
	/**
	 * 错误信息
	 */
	private String errorMsg;
	/**
	 * 发送渠道
	 */
	private Integer channel;
}
