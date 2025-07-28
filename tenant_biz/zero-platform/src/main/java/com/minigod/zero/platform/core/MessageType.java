package com.minigod.zero.platform.core;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/12/11 9:45
 * @description：
 */
public enum MessageType {
	SMS("sms",1,"短信"),
	PUSH("push",2,"推送"),
	EMAIL("email",3,"邮件");

	private String code;
	private Integer value;
	private String desc;

	MessageType(String code,Integer value,String desc){
		this.code = code;
		this.desc = desc;
		this.value = value;
	}

	public String getCode() {
		return code;
	}

	public Integer getValue(){
		return value;
	}

}
