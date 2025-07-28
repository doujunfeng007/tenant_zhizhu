package com.minigod.auth.events.handler;

import com.minigod.auth.service.AppUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/7/29 10:55
 * @description：登录失败处理
 */
public interface AuthenticationFailureHandler {
	void hand(Authentication authentication, AuthenticationException exception);

	int order();
}
