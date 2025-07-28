package com.minigod.zero.customer.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/8/1 17:30
 * @description：
 */
public class RegexUtils {
	/**
	 * 包含数字，大写字母，小写字母
	 */
	private final static String LETTER_FORMAT = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).+";
	/**
	 * 包含汉字
	 */
	private final static String CONTAINS_CHINESE =  "[\\u4e00-\\u9fa5]+";

	/**
	 * 密码规则校验
	 * 至少一个数字、至少一个字母和至少一个特殊符号
	 * @param pwd
	 * @return
	 */
	public static boolean passwordRuleVerification(String pwd){
		if(StringUtils.isEmpty(pwd)){
			return false;
		}
		return pwd.matches(LETTER_FORMAT);
	}

	/**
	 * 判断字符串是否包含汉字
	 * @param str 要判断的字符串
	 * @return 如果包含汉字则返回true，否则返回false
	 */
	public static boolean containsChinese(String str) {
		return str.matches(".*" + CONTAINS_CHINESE + ".*");
	}
}
