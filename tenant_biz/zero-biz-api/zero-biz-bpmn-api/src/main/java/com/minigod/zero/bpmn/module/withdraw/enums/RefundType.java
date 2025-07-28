package com.minigod.zero.bpmn.module.withdraw.enums;


/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/9/11 15:37
 * @description：
 */
public enum RefundType {
	NO_HANDLING_FEE(0,"无手续费"),
	INCLUDING_HANDLING_FEES(1,"含手续费");

	private Integer type;
	private String name;

	private Integer getType(){
		return type;
	}

	RefundType(Integer type, String name) {
		this.type = type;
		this.name = name;
	}

	public static String getName(Integer type) {
		for (RefundType c : RefundType.values()) {
			if (c.type.equals(type)) {
				return c.name;
			}
		}
		return null;
	}
}
