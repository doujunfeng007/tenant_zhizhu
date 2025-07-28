package com.minigod.zero.biz.common.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/7/12 15:26
 * @description：银行账户类型
 */
public enum BankAccountType {
	HKd_ACCOUNT(1, "港币账户"),
	USD_ACCOUNT(2, "美元账户"),
	CNY_ACCOUNT(3, "人民币账户"),
	OMNIBUS_ACCOUNT(0, "综合账户");

	private Integer code;

	private String desc;

	BankAccountType(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public static BankAccountType getByCode(Integer code) {
		if (code == null) {
			return OMNIBUS_ACCOUNT;
		}
		for (BankAccountType type : BankAccountType.values()) {
			if (type.getCode().equals(code)) {
				return type;
			}
		}
		return OMNIBUS_ACCOUNT;
	}

	public Integer getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public static Map<Integer, String> getMap() {
		Map<Integer, String> map = new HashMap<>();
		for (BankAccountType type : BankAccountType.values()) {
			map.put(type.getCode(), type.getDesc());
		}
		return map;
	}
}
