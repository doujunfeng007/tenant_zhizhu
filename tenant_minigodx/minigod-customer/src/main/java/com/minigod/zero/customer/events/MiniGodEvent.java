package com.minigod.zero.customer.events;

import org.springframework.context.ApplicationEvent;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/9/12 16:06
 * @description：
 */
public class MiniGodEvent extends ApplicationEvent {

	public MiniGodEvent(MiniGodEventSource source) {
		super(source);
	}



}
