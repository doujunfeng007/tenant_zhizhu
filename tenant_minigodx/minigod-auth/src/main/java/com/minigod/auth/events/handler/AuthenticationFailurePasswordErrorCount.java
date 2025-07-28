package com.minigod.auth.events.handler;

import com.minigod.auth.exception.PasswordErrorException;
import com.minigod.auth.service.AppUserDetails;
import com.minigod.auth.utils.AccountLoginUtils;
import com.minigod.zero.core.launch.constant.TokenConstant;
import com.minigod.zero.core.tool.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/8/1 18:35
 * @description：密码输入错误统计次数
 */
@Component
public class AuthenticationFailurePasswordErrorCount implements AuthenticationFailureHandler{
	@Autowired
	private AccountLoginUtils accountLoginUtils;

	@Override
	public void hand(Authentication authentication, AuthenticationException exception) {
		Boolean flag = exception instanceof PasswordErrorException;
		if (!flag){
			return;
		}
		Object detail = authentication.getDetails();
		if (detail == null){
			return;
		}
		AppUserDetails userDetails = (AppUserDetails)detail;

		String userName = userDetails.getUsername();
		String areaCode = userDetails.getAreaCode();
		String tenantId = userDetails.getTenantId();
		accountLoginUtils.failureCount(tenantId, userName,areaCode);
	}

	@Override
	public int order() {
		return Order.FAILURE.FAILURE_PASSWORD_ERROR_COUNT.getCode();
	}
}
