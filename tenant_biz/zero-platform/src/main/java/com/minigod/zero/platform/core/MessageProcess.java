package com.minigod.zero.platform.core;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/12/19 9:45
 * @description： 消息持久化处理
 */
public interface MessageProcess {

	/**
	 * 保存消息记录
	 * @param message
	 */
	void saveMessageRecord(Message message);

	/**
	 * 修改消息记录
	 * @param sendResult
	 * @param message
	 */
	void updateMessageRecord(SendResult sendResult,Message message);
}
