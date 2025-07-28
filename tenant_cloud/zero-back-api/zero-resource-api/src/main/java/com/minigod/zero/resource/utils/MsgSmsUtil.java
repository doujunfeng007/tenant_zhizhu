package com.minigod.zero.resource.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Title: MsgSmsUtil.java
 * @Description: 消息通道服务工具类
 * @Copyright: © 2014 sunline
 * @Company: sunline
 *
 * @author sunline
 * @date 2014-10-19 下午7:38:18
 * @version v1.0
 */
@Slf4j
public class MsgSmsUtil {

	/**
	 * 判断应用请求发送短信的信息是否符合规则
	 *
	 * @param sMobile
	 *            手机号
	 * @param sContent
	 *            内容
	 * @param sExtension
	 *            业务扩展码
	 * @return 校验是否通过，是：true；否：false
	 */
	public static boolean checkInfo(String sMobile, String sContent, String sExtension) {
		// 校验是否通过的标志
		boolean isValid = false;
		// 信息校验标志
		boolean isMobileReg = false;
		boolean isContentReg = false;
		boolean isExtReg = false;
		// 信息校验
		isMobileReg = isMobileNo(sMobile); // 正则校验手机号是否符合规则   2018-1-16
		isContentReg = isNotTooLang(sContent, 500); // 校验短信信息的长度，不超过500个字符（中文英文都算一个字符）
		isExtReg = checkNumber(sExtension, 4); // 判断业务扩展码是否是4位纯数字
		// 校验成功
		if (isMobileReg && isContentReg && isExtReg) {
			isValid = true;
		}
		return isValid;
	}

	/**
	 * 号码确认时间：2014-12-25 16:00:00，后续修改请添加时间
	 * 检验手机号的URL地址：http://www.showji.com
	 * 判断手机号是否合法，可以提出来作为公共方法 要更加准确的匹配手机号码只匹配11位数字是不够的，比如说就没有以144开始的号码段，
	 * 故先要整清楚现在已经开放了多少个号码段，国家号码段分配如下：
	 * 移动：134、135、136、137、138、139、147、150、151、152、157、158、159、178、182、183、184、187、188
	 * 联通：130、131、132、145、155、156、176、185、186
	 * 电信：133、153、170、177、180、181、189
	 *
	 * @param sMobile
	 *            手机号
	 * @return 校验是否通过，是：true；否：false
	 */
	private static boolean isMobileNo(String sMobile) {
		// 正则校验手机号是否符合规则
		// Pattern p = Pattern.compile("^((13[0-9])|(14[5,7])|(15[^4,\\D])|(17[0,6-8])|(18[0-9]))\\d{8}$");
		Pattern p = Pattern.compile("(\\+?\\d{1,5}-)?\\d{6,11}"); // 新规则，放宽为11位纯数字
		Matcher m = p.matcher(sMobile);
		return m.matches();
	}

	public static boolean isCnMobileNo(String sMobile) {
		Pattern p = Pattern.compile("(\\+?86-)?\\d{11}");
		Matcher m = p.matcher(sMobile);
		return m.matches();
	}

	public static boolean isHkMacaoTaiwanMobileNo(String sMobile) {
		Pattern p = Pattern.compile("(\\+?852-)?\\d{8}|(\\+?853-)?\\d{8}|(\\+?886-)?\\d{10}");
		Matcher m = p.matcher(sMobile);
		return m.matches();
	}

	/**
	 * 校验信息长度是否超长，规则：如同MySql的规则，中英文都算一个字符
	 *
	 * @param s
	 *            要校验的内容
	 * @param i
	 *            规定的长度
	 * @return 校验是否通过，是：true；否：false
	 */
	private static boolean isNotTooLang(String s, int i) {
		boolean isLong = true;
		if (s.length() > i) {
			isLong = false;
		}
		return isLong;
	}

	/**
	 * 判断给定的字符串是否为指定长度的纯数字
	 *
	 * @param s
	 *            要检验的字符串
	 * @param i
	 *            要校验的长度
	 * @return 校验是否通过，是：true；否：false
	 */
	public static boolean checkNumber(String s, int i) {
		boolean isInt = Pattern.matches("\\d{" + i + "}", s);
		if (isInt) {
			return s.length() == i;
		}
		return false;
	}

	public static boolean checkInfo(String sMobile, String sContent) {
		// 校验是否通过的标志
		boolean isValid = false;
		// 信息校验标志
		boolean isMobileReg = false;
		boolean isContentReg = false;
		// 信息校验
		isMobileReg = isMobileNo(sMobile); // 正则校验手机号是否符合规则
		isContentReg = isNotTooLang(sContent, 500); // 校验短信信息的长度，不超过500个字符（中文英文都算一个字符）
		// 校验成功
		if (isMobileReg && isContentReg) {
			isValid = true;
		}
		return isValid;
	}

}
