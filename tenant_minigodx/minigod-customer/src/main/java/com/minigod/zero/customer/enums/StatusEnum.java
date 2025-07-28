package com.minigod.zero.customer.enums;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/5/7 14:01
 * @description：
 */
public enum StatusEnum {
	YES(1, "是"), NO(0, "不是");

	int code;
	String info;

	StatusEnum(int code, String info) {
		this.code = code;
		this.info = info;
	}

	public int getCode() {
		return code;
	}
	public String getInfo() {
		return info;
	}
}
