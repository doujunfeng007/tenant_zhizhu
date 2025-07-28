package com.minigod.auth.events.handler;

import com.minigod.auth.service.AppUserDetails;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/7/29 10:55
 * @description：登录成功处理
 */
public interface AuthenticationSuccessHandler {
	void hand(AppUserDetails userDetails);

	int order();
}
