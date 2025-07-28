package com.minigod.auth.events.listener;

import com.minigod.auth.events.handler.AuthenticationSuccessHandler;
import com.minigod.auth.events.factory.AuthenticationSuccessHandlerFactory;
import com.minigod.auth.granter.captcha.CaptchaAuthenticationToken;
import com.minigod.auth.service.AppUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
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
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

	@Autowired
	private ThreadPoolExecutor threadPoolExecutor;

	@Autowired
	private AuthenticationSuccessHandlerFactory authenticationSuccessHandlerFactory;

	@Override
	public void onApplicationEvent(AuthenticationSuccessEvent event) {
		Authentication authentication = event.getAuthentication();
		AppUserDetails userDetails = null;
		//验证码模式
		if (authentication instanceof CaptchaAuthenticationToken){
			CaptchaAuthenticationToken authenticationToken = (CaptchaAuthenticationToken) authentication;
			userDetails = (AppUserDetails) authenticationToken.getPrincipal();
		}
		//密码模式
		if (authentication instanceof UsernamePasswordAuthenticationToken
			&& authentication.getPrincipal() instanceof AppUserDetails){
			userDetails = (AppUserDetails) authentication.getPrincipal();
		}
		if (userDetails == null){
			return;
		}
		TreeMap<Integer, AuthenticationSuccessHandler> handlerTreeMap = authenticationSuccessHandlerFactory.getHandlerTreeMap();
		for (Map.Entry entry: handlerTreeMap.entrySet()) {
			try {
				AuthenticationSuccessHandler handler = (AuthenticationSuccessHandler) entry.getValue();
				AppUserDetails finalUserDetails = userDetails;
				threadPoolExecutor.execute(new Runnable() {
					@Override
					public void run() {
						handler.hand(finalUserDetails);
					}
				});
			} catch (Exception e) {
				log.error("登录成功事件执行失败：{}",e.getMessage());
			}
		}
	}
}
