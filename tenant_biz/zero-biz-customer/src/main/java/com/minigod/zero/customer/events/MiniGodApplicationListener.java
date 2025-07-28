package com.minigod.zero.customer.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/9/12 16:13
 * @description：
 */
@Slf4j
@Component
public class MiniGodApplicationListener implements ApplicationListener<MiniGodEvent> {
	@Autowired
	private EventHandlerFactory eventHandlerFactory;

	@Override
	public void onApplicationEvent(MiniGodEvent event) {
		MiniGodEventSource source = (MiniGodEventSource) event.getSource();
		eventHandlerFactory.hand(source);
	}
}
