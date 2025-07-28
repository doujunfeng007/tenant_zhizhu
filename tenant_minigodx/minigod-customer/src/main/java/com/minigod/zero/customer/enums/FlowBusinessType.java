package com.minigod.zero.customer.enums;

import com.minigod.zero.core.tool.utils.StringUtil;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/8/21 17:27
 * @description：流水操作类型
 */
public enum FlowBusinessType {
	AVAILABLE_DEPOSIT (1,"可用存入"),
	AVAILABLE_DEDUCTIONS (2,"可用扣除"),
	AVAILABLE_TO_FREEZE (3,"可用转冻结"),
	FREEZE_DEDUCTIONS (4,"冻结扣除"),
	FREEZE_TO_AVAILABLE  (5,"冻结转可用"),
	TRANSITED_DEPOSIT(6,"在途存入"),
	TRANSITED_TO_AVAILABLE(7,"在途转可用");

	private String desc;
	private Integer code;

	FlowBusinessType(Integer code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public Integer getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public static String getByCode(String code) {
		if (StringUtil.isBlank(code)){
			return null;
		}
		for (FlowBusinessType type : FlowBusinessType.values()) {
			if (Integer.valueOf(code).equals(type.getCode())) {
				return type.getDesc();
			}
		}
		return null;
	}
}
