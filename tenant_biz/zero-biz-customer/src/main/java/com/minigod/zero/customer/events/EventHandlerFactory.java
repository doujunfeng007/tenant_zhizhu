package com.minigod.zero.customer.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.TreeMap;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/9/12 16:17
 * @description：
 */
@Slf4j
@Component
public class EventHandlerFactory implements ApplicationContextAware {

	private final  static TreeMap<String, MiniGodEventHandler> HANDLER_MAP = new TreeMap<>();

	public void hand(MiniGodEventSource source){
		MiniGodEventHandler handler = HANDLER_MAP.get(source.getEventType().getCode());
		handler.hand(source.getParam());
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		Map<String, MiniGodEventHandler> handlerMap = applicationContext.getBeansOfType(MiniGodEventHandler.class);
		if (handlerMap != null && handlerMap.size() > 0){
			for (Map.Entry entry: handlerMap.entrySet()) {
				MiniGodEventHandler handler = (MiniGodEventHandler) entry.getValue();
				HANDLER_MAP.put(handler.eventType(),handler);
			}
		}
	}
}
