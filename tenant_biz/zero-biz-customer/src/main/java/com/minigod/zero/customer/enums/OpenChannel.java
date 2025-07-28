package com.minigod.zero.customer.enums;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/7/18 10:39
 * @description：
 */
public enum OpenChannel {
	h5_on_Line(1,"H5线上开户"),
	APP_ON_LINE(2,"APP线上开户"),
	OFFLINE(3,"线下后台开户");

	private Integer code;
	private String desc;

	OpenChannel(Integer code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public Integer getCode() {
		return code;
	}
}
