package com.minigod.zero.platform.core;

import java.util.regex.Pattern;

/**
 * @Author: guangjie.liao
 * @Date: 2024/4/14 15:03
 * @Description: 手机号校验
 */
public class ValidateUtil {
	private static String AREA_CODE = "86";
	// 校验手机号码的正则表达式
	private static final String MOBILE_PHONE_PATTERN = "^1[3-9]\\d{9}$";

	// 校验固定电话的正则表达式
	private static final String FIXED_PHONE_PATTERN = "^(0\\d{2,3}[- ]?\\d{7,8})$";

	// 校验国际电话号码的正则表达式
	// 这个表达式假设 "+" 后面至少跟着一个数字，并且整个号码长度不超过20位
	private static final String INTERNATIONAL_PHONE_PATTERN = "^\\+[1-9]\\d{1,19}$";

	public static boolean validatePhone(String areaCode,String phone){

		if (AREA_CODE.equals(areaCode)){
			return isValidMobilePhone(phone) || isValidFixedPhone(phone);
		}else{
			String checkPhone = "+"+areaCode+phone;
			return isValidInternationalPhone(checkPhone);
		}
	}

	public static boolean isValidMobilePhone(String phoneNumber) {
		return phoneNumber != null && Pattern.matches(MOBILE_PHONE_PATTERN, phoneNumber);
	}

	public static boolean isValidFixedPhone(String phoneNumber) {
		return phoneNumber != null && Pattern.matches(FIXED_PHONE_PATTERN, phoneNumber);
	}

	public static boolean isValidInternationalPhone(String phoneNumber) {
		return phoneNumber != null && Pattern.matches(INTERNATIONAL_PHONE_PATTERN, phoneNumber);
	}

	public static void main(String[] args) {
		System.out.println(validatePhone("86","182000000001"));
	}
}
