package com.minigod.zero.customer.events;

import lombok.Data;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/9/12 16:10
 * @description：
 */
@Data
public class MiniGodEventSource extends Object{
	/**
	 * 参数
	 */
	private Object param;
	/**
	 * {@link EventType}
	 */
	private EventType eventType;

	public MiniGodEventSource(Object param,EventType eventType){
		this.eventType = eventType;
		this.param = param;
	}
}
