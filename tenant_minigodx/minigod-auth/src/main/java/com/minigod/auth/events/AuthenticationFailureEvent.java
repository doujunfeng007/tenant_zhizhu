package com.minigod.auth.events;

import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/8/1 18:18
 * @description：
 */
public class AuthenticationFailureEvent extends AbstractAuthenticationFailureEvent {

	public AuthenticationFailureEvent(Authentication authentication, AuthenticationException exception) {
		super(authentication,exception);
	}
}
