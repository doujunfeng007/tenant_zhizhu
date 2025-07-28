package com.minigod.zero.cust.service;


import com.minigod.zero.core.tool.api.R;

public interface ICustValidationService {

	/**
	 * 验证注册密码格式
	 * @param pwd
	 * @param key
	 * @return
	 */
	//R validationRegPwd(String pwd,String key);

	/**
	 * 验证手机号
	 * @param certCode
	 * @return
	 */
	R validatePhone(String certCode);

	/**
	 * 验证邮箱格式
	 * @param email
	 * @return
	 */
	boolean checkEmailFormat(String email);

	/**
	 * 验证手机号是否注册
	 * @param phoneNum
	 * @return
	 */
	R validationPhoneStatus(Integer custType, String areaCode, String phoneNum);
}
