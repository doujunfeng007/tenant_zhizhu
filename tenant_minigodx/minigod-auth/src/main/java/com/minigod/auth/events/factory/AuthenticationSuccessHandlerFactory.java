package com.minigod.auth.events.factory;

import com.minigod.auth.events.handler.AuthenticationSuccessHandler;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.TreeMap;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/7/29 11:01
 * @description：授权成功类工厂
 */
@Component
public class AuthenticationSuccessHandlerFactory implements ApplicationContextAware {

	private final  static TreeMap<Integer, AuthenticationSuccessHandler> handlerTreeMap = new TreeMap<>();

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		Map<String,AuthenticationSuccessHandler> handlerMap = applicationContext.getBeansOfType(AuthenticationSuccessHandler.class);
		if (handlerMap != null && handlerMap.size() > 0){
			for (Map.Entry entry: handlerMap.entrySet()) {
				AuthenticationSuccessHandler handler = (AuthenticationSuccessHandler) entry.getValue();
				handlerTreeMap.put(handler.order(),handler);
			}
		}
	}

	public TreeMap<Integer, AuthenticationSuccessHandler> getHandlerTreeMap() {
		return handlerTreeMap;
	}
}
