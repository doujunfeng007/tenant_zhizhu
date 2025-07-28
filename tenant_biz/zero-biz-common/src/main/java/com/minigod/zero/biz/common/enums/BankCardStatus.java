package com.minigod.zero.biz.common.enums;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/7/13 17:08
 * @description：用户绑定银行卡状态
 */
public enum BankCardStatus {
	INVALID(0,"无效"),
	EFFECTIVE(1,"有效");
	private Integer code;
	private String desc;

	 BankCardStatus(Integer code,String desc){
		 this.code = code;
		 this.desc = desc;
	 }

	public Integer getCode() {
		return code;
	}
}
