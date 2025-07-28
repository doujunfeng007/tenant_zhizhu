package com.minigod.zero.customer.enums;

/**
 * @ClassName: com.minigod.zero.customer.enums.Statement
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/5/31 15:03
 * @Version: 1.0
 */
public enum StatementEnum {
	DAY(1, "日结单"),
	MONTH(2, "月结单");

	int code;
	String info;

	StatementEnum(int code, String info) {
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
