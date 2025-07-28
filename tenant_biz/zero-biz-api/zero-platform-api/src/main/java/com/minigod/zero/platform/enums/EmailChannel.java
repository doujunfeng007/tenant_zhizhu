package com.minigod.zero.platform.enums;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/8/16 10:38
 * @description：
 */
public enum EmailChannel {
	SEND_CLOUD(1, "sendCloud");

	private Integer code;
	private String name;

	EmailChannel(Integer code, String name) {
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
