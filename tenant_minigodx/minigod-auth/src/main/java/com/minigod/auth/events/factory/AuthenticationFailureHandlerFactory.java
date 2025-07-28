package com.minigod.auth.events.factory;

import com.minigod.auth.events.handler.AuthenticationFailureHandler;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.TreeMap;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/7/29 11:01
 * @description：授权失败处理类工厂
 */
@Component
public class AuthenticationFailureHandlerFactory implements ApplicationContextAware {

	private final  static TreeMap<Integer, AuthenticationFailureHandler> handlerTreeMap = new TreeMap<>();

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		Map<String,AuthenticationFailureHandler> handlerMap = applicationContext.getBeansOfType(AuthenticationFailureHandler.class);
		if (handlerMap != null && handlerMap.size() > 0){
			for (Map.Entry entry: handlerMap.entrySet()) {
				AuthenticationFailureHandler handler = (AuthenticationFailureHandler) entry.getValue();
				handlerTreeMap.put(handler.order(),handler);
			}
		}
	}

	public TreeMap<Integer, AuthenticationFailureHandler> getHandlerTreeMap() {
		return handlerTreeMap;
	}
}
