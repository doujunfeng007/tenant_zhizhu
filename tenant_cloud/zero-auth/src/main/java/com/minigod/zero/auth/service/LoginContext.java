package com.minigod.zero.auth.service;

import com.alibaba.nacos.common.utils.StringUtils;
import com.minigod.zero.auth.utils.TokenUtil;
import com.minigod.zero.core.tool.utils.WebUtil;
import lombok.Data;
import org.apache.commons.lang3.Validate;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/10/25 15:26
 * @description：
 */
@Data
public class LoginContext {
	private String areaCode;
	private String captchaKey;
	private String captchaCode;
	private String userName;
	private String loginAccount;
	private String tenantId;

	public LoginContext(){

	}

	public LoginContext(Map<String, String> parameters){
		this.tenantId = initTenantId();
		this.userName = parameters.get("username");
		this.captchaKey = initCaptchaKey();
		this.captchaCode = initCaptchaCode();
		this.loginAccount = parameters.get("loginAccount");
		if (!isEmail() && loginAccount.indexOf("-") > 0){
			String[] loginAccountStr = loginAccount.split("-");
			this.areaCode = loginAccountStr[0];
			this.loginAccount = loginAccountStr[1];
		}
	}

	public void paramCheck(){
		Validate.notNull(userName, "userName不能为空");
		Validate.notNull(tenantId, "tenantId不能为空");
		Validate.notNull(captchaKey, "captchaKey不能为空");
		Validate.notNull(captchaCode, "captchaCode不能为空");
		Validate.notNull(loginAccount, "loginAccount不能为空");
		if (loginAccount.indexOf("@") < 0 ){
			Validate.notNull(areaCode, "手机区号缺失");
		}
	}

	private String initTenantId() {
		HttpServletRequest request = WebUtil.getRequest();
		String headerTenant = request.getHeader(TokenUtil.TENANT_HEADER_KEY);
		String paramTenant = request.getParameter(TokenUtil.TENANT_PARAM_KEY);
		return StringUtils.hasText(headerTenant) ? headerTenant : paramTenant;
	}

	private String initCaptchaCode() {
		HttpServletRequest request = WebUtil.getRequest();
		String headerTenant = request.getHeader(TokenUtil.CAPTCHA_HEADER_CODE);
		String paramTenant = request.getParameter(TokenUtil.CAPTCHA_HEADER_CODE);
		return StringUtils.hasText(headerTenant) ? headerTenant : paramTenant;
	}

	private String initCaptchaKey() {
		HttpServletRequest request = WebUtil.getRequest();
		String headerTenant = request.getHeader(TokenUtil.CAPTCHA_HEADER_KEY);
		String paramTenant = request.getParameter(TokenUtil.CAPTCHA_HEADER_KEY);
		return StringUtils.hasText(headerTenant) ? headerTenant : paramTenant;
	}

	public boolean isEmail(){
		return this.loginAccount.indexOf("@") > 0 ;
	}
}
