package com.minigod.auth.events.handler;

import com.minigod.zero.core.tool.api.R;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/4/26 17:01
 * @description：
 */
@RestControllerAdvice
public class ResponseHandler implements ResponseBodyAdvice {
	@Override
	public boolean supports(MethodParameter methodParameter, Class aClass) {
		return true;
	}

	@Override
	public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
		// 登录成功 统一返回
		if(o instanceof OAuth2AccessToken){
			OAuth2AccessToken token = (OAuth2AccessToken)o;
			return R.data(token);
		}
		return o;
	}
}
