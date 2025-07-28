package com.minigod.zero.customer.enums;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/7/11 18:43
 * @description：銀行類型
 */
public enum BankType {
	HK_BANK(1,"香港银行"),
	CHN_BANK(2,"大陆银行"),
	OTHER_BANK(3,"其他银行");

	private Integer code;
	private String desc;

	BankType(Integer code, String desc){
		this.code = code;
		this.desc = desc;
	}

	public Integer getCode(){
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public static String getDesc(Integer index) {
		for (BankType c : BankType.values()) {
			if (c.getCode().equals(index)) {
				return c.getDesc();
			}
		}
		return null;
	}
}
