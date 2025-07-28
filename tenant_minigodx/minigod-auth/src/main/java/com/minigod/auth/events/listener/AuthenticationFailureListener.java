package com.minigod.auth.events.listener;

import com.minigod.auth.events.AuthenticationFailureEvent;
import com.minigod.auth.events.handler.AuthenticationFailureHandler;
import com.minigod.auth.events.factory.AuthenticationFailureHandlerFactory;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/7/29 10:44
 * @description：授权成功事件
 */
@Slf4j
@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureEvent> {

	@Autowired
	private ThreadPoolExecutor threadPoolExecutor;

	@Autowired
	private AuthenticationFailureHandlerFactory authenticationFailureHandlerFactory;

	@Override
	public void onApplicationEvent(AuthenticationFailureEvent event) {
		Authentication authentication = event.getAuthentication();
		TreeMap<Integer, AuthenticationFailureHandler> handlerTreeMap = authenticationFailureHandlerFactory.getHandlerTreeMap();
		for (Map.Entry entry: handlerTreeMap.entrySet()) {
			try {
				AuthenticationFailureHandler handler = (AuthenticationFailureHandler) entry.getValue();
				threadPoolExecutor.execute(new Runnable() {
					@Override
					public void run() {
						handler.hand(authentication,event.getException());
					}
				});
			} catch (Exception e) {
				log.error("登录失败事件执行失败：{}",e.getMessage());
			}
		}
	}
}
