package com.minigod.zero.platform.enums;


/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/8/16 10:38
 * @description：
 */
public enum PushChannel {
	JG(1, "极光推送");

	private Integer code;
	private String name;

	PushChannel(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

}
