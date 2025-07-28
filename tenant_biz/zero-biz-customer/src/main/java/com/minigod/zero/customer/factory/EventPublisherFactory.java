package com.minigod.zero.customer.factory;

import com.minigod.zero.core.tool.utils.SpringUtil;
import com.minigod.zero.customer.events.MiniGodApplicationEventPublisher;
import com.minigod.zero.customer.events.MiniGodEvent;
import com.minigod.zero.customer.events.MiniGodEventSource;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/9/12 15:52
 * @description：事件发布工具类
 */
public class EventPublisherFactory {

	private static MiniGodApplicationEventPublisher applicationEventPublisher;

	/**
	 *
	 * @return
	 */
	public static MiniGodApplicationEventPublisher applicationEventPublisher() {
		if (applicationEventPublisher == null) {
			applicationEventPublisher = SpringUtil.getBean(MiniGodApplicationEventPublisher.class);
		}
		return applicationEventPublisher;
	}

	/**
	 * 事件发布
	 * @param source
	 */
	public static void publishEvent(MiniGodEventSource source){
		applicationEventPublisher().publisher(new MiniGodEvent(source));
	}
}
