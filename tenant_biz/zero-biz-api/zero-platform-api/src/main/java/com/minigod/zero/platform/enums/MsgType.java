package com.minigod.zero.platform.enums;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/8/13 15:32
 * @description：消息类型
 */
public enum MsgType {
	SMS(1,"短信"),
	PUSH(2,"推送"),
	EMAIL(3,"邮件");

	private Integer code;
	private String desc;

	MsgType(Integer code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public Integer getCode() {
		return code;
	}
}
