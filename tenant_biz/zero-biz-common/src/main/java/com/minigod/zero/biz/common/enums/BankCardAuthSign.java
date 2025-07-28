package com.minigod.zero.biz.common.enums;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/7/13 16:21
 * @description：银行卡审核状态
 */
public enum BankCardAuthSign {
	NOT_CERTIFIED(1,"未认证"),
	TO_BE_CERTIFIED(2,"待认证"),
	CERTIFIED(3,"已认证");

	private Integer code;
	private String desc;

	BankCardAuthSign(Integer code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public Integer getCode(){
		return code;
	}
}
