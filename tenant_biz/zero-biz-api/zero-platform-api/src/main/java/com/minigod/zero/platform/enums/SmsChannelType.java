package com.minigod.zero.platform.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 短信通道
 *
 * @author Zhe.Xiao
 */
public enum SmsChannelType {
	CL(0, "创蓝"),
	Tencent(2, "腾讯云"),
	ALIYUN(1, "阿里云");

	private Integer code;
	private String name;

	private static Map<Integer, SmsChannelType> smsChannelMap = new HashMap<>();

	static {
		for (SmsChannelType value : SmsChannelType.values()) {
			smsChannelMap.put(value.getCode(), value);
		}
	}

	SmsChannelType(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public static Integer next(Integer codeNow) {
		return values()[(smsChannelMap.get(codeNow).ordinal() + 1) % values().length].getCode();
	}

	public static boolean containsChannel(String code) {
		return smsChannelMap.keySet().contains(code);
	}

	public static String fromCode(Integer code) {
		for (SmsChannelType action : SmsChannelType.values()) {
			if (action.getCode() == code) {
				return action.getName();
			}
		}
		return null;
	}

}
