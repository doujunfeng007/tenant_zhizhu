/*
 * Copyright (c)  2022. ZSDP Inc. All rights reserved.
 */
package com.minigod.auth.service;

import com.minigod.auth.constant.AuthConstant;
import com.minigod.common.constant.CommonConstant;
import com.minigod.common.exceptions.BusinessException;
import com.minigod.zero.core.i18n.utils.I18nUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.minigod.zero.customer.emuns.CustomerStatus;
import com.minigod.zero.customer.fegin.ICustomerInfoClient;
import com.minigod.zero.customer.vo.CustomerInfoVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 用户信息
 *
 * @author zsdp
 */
@Slf4j
@Primary
@Service
@AllArgsConstructor
public class AuthorizationServicesUserDetails implements MinigodUserDetailsService {
	private final ICustomerInfoClient iCustomerInfoClient;
	private static final ThreadLocal<AppUserDetails> APP_USER_LOCAL = new ThreadLocal<>();
	@Override
	public AppUserDetails loadUserByUsername(String username,String areaCode,String tenantId,String param) {
		log.info("登录查询用户信息username={},tenantId={}",username,tenantId);
		String  loginType = WebUtil.getRequest().getHeader(AuthConstant.LOGIN_TYPE);
		R<CustomerInfoVO> result = null;
		if (AuthConstant.LOGIN_SOURCE.equalsIgnoreCase(loginType)){
			result = iCustomerInfoClient.loadCustomerByAccount(username,tenantId);
		}else{
			result = iCustomerInfoClient.loadCustomerByUsername(username,areaCode,tenantId,param);
		}
		if (!result.isSuccess()){
			throw new BusinessException(result.getMsg());
		}
		CustomerInfoVO entity = result.getData();
		if (entity.getId() == null){
			return null;
		}
		validateCustomerStatus(entity.getStatus());

		AppUserDetails userDetails = new AppUserDetails(entity);
		//兼容理财账号登录，给以默认区号
		if (AuthConstant.LOGIN_SOURCE.equalsIgnoreCase(loginType)){
			userDetails.setAreaCode(AuthConstant.LOGIN_SOURCE);
			userDetails.setUser_name(entity.getAccount());
		}
		APP_USER_LOCAL.set(userDetails);
		return userDetails;
	}

	@Override
	public AppUserDetails loadUserBySbuAccount(String accountId, String subAccount) {
		R<CustomerInfoVO> result = iCustomerInfoClient.loadCustomerBySubAccount(accountId,subAccount);
		if (!result.isSuccess()){
			throw new BusinessException(result.getMsg());
		}
		CustomerInfoVO entity = result.getData();
		if (entity.getId() == null){
			return null;
		}
		validateCustomerStatus(entity.getStatus());
		AppUserDetails userDetails = new AppUserDetails(entity);
		userDetails.setUser_name(entity.getAccount());
		APP_USER_LOCAL.set(userDetails);
		return userDetails;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUserDetails userDetails = APP_USER_LOCAL.get();
		APP_USER_LOCAL.remove();
		return userDetails;
	}

	/**
	 * 客户状态校验
	 * @param customerStatus
	 */
	private void validateCustomerStatus(Integer customerStatus){
		if (customerStatus == CustomerStatus.STOP_USE.getCode()){
			throw new DisabledException(I18nUtil.getMessage(CommonConstant.ACCOUNT_DEACTIVATED));
		}
		if (customerStatus == CustomerStatus.LOCKING.getCode()){
			throw new LockedException(I18nUtil.getMessage(CommonConstant.ACCOUNT_LOCKED));
		}
		if (customerStatus == CustomerStatus.LOGOUT.getCode()){
			throw new CredentialsExpiredException(I18nUtil.getMessage(CommonConstant.ACCOUNT_HAS_BEEN_CANCELLED));
		}
	}
}
