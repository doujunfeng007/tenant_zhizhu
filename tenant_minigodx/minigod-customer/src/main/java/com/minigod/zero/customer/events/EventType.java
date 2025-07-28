package com.minigod.zero.customer.events;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/9/12 16:19
 * @description：
 */
public enum EventType {
	REGISTER_SUCCESS("REGISTER_SUCCESS","注册成功");

	private String code;
	private String desc;

	public String getCode(){
		return this.code;
	}

	EventType(String code,String desc){
		this.code = code;
		this.desc = desc;
	}
}
