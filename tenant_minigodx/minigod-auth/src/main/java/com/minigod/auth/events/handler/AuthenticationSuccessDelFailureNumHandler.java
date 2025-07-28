package com.minigod.auth.events.handler;

import com.minigod.auth.service.AppUserDetails;
import com.minigod.auth.utils.AccountLoginUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/7/29 10:56
 * @description：登录成功发删除失败次数
 */
@Slf4j
@Component
public class AuthenticationSuccessDelFailureNumHandler implements AuthenticationSuccessHandler{
	@Autowired
	private AccountLoginUtils accountLoginUtils;

	@Override
	public void hand(AppUserDetails userDetails) {
		accountLoginUtils.delFailureCount(userDetails.getTenantId(),userDetails.getUsername(),userDetails.getAreaCode());
	}

	@Override
	public int order() {
		return Order.SUCCESS.DEL_FAILURE_NUM.getCode();
	}
}
