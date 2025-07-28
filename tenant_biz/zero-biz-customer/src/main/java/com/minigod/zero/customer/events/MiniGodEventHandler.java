package com.minigod.zero.customer.events;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/9/12 16:29
 * @description：
 */
public interface MiniGodEventHandler {
	void hand(Object param);

	String eventType();
}
