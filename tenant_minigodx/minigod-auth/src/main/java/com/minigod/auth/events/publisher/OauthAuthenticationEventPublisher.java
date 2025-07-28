package com.minigod.auth.events.publisher;

import com.minigod.auth.events.AuthenticationFailureEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.filter.OAuth2AuthenticationFailureEvent;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/7/29 10:34
 * @description：
 */
@Slf4j
public class OauthAuthenticationEventPublisher implements AuthenticationEventPublisher, ApplicationEventPublisherAware {

	private ApplicationEventPublisher applicationEventPublisher;

	public OauthAuthenticationEventPublisher(ApplicationEventPublisher applicationEventPublisher){
		this.applicationEventPublisher = applicationEventPublisher;
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}

	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {
		applicationEventPublisher.publishEvent(new AuthenticationSuccessEvent(authentication));
	}

	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
		applicationEventPublisher.publishEvent(new AuthenticationFailureEvent(authentication,exception));
	}
}
