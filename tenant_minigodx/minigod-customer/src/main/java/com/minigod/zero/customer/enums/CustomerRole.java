package com.minigod.zero.customer.enums;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/9/14 16:03
 * @description：
 */
public enum CustomerRole {
	DA(1,"DA","F"),
	SUB_DA(2,"subDA","S"),
	PI(3,"PI","P"),
	ORDINARY(4,"普通用户","N"),
	MARKET_MAKER(5,"做市商","M"),
	TRADER(6,"交易员","T"),
	DISTRIBUTION_MANAGEMENT(7,"分销管理","D");

	private Integer code;
	private String desc;
	private String prefix;

	CustomerRole(Integer code,String desc,String prefix){
		this.code = code;
		this.desc = desc;
		this.prefix = prefix;
	}
	public Integer getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public String getPrefix(){
		return this.prefix;
	}

	public static String getDesc(Integer index) {
		for (CustomerRole c : CustomerRole.values()) {
			if (c.getCode().equals(index)) {
				return c.getDesc();
			}
		}
		return null;
	}

	public static String getPrefix(Integer index) {
		for (CustomerRole c : CustomerRole.values()) {
			if (c.getCode().equals(index)) {
				return c.getPrefix();
			}
		}
		return null;
	}
}
