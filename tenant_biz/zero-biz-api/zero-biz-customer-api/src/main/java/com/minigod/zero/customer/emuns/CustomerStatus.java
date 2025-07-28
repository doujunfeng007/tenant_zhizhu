package com.minigod.zero.customer.emuns;

/**
 * 客户状态枚举
 */
public enum CustomerStatus {
	DEFAULT(-1, "未知"),
	STOP_USE(0, "停用"),
	NORMAL(1, "正常"),
	LOCKING(2, "锁定"),
	LOGOUT(3, "注销"),
	FREEZE(4, "冻结");
	private Integer code;
	private String desc;

	CustomerStatus(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public Integer getCode() {
		return code;
	}

	public String getDesc() {
		return this.desc;
	}

	public static CustomerStatus getByCode(Integer code) {
		if (code == null) {
			return DEFAULT;
		}
		for (CustomerStatus type : CustomerStatus.values()) {
			if (type.getCode().equals(code)) {
				return type;
			}
		}
		return DEFAULT;
	}
}
