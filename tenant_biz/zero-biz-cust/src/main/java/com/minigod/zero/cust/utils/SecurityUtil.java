package com.minigod.zero.cust.utils;

/**
 * @author: chen
 * @Project: zero
 * @Pcakage: com.minigod.zero.common.utils.SecurityUtil
 * @Date: 2023年02月15日 19:52
 * @Description:
 */
public class SecurityUtil {

	public SecurityUtil() {
	}

	public static boolean isBlank(String str) {
		int strLen;
		if (str != null && (strLen = str.length()) != 0) {
			for(int i = 0; i < strLen; ++i) {
				if (!Character.isWhitespace(str.charAt(i))) {
					return false;
				}
			}

			return true;
		} else {
			return true;
		}
	}
}
