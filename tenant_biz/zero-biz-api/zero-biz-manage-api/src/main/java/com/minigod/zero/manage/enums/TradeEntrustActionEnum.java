package com.minigod.zero.manage.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * 模拟交易委托的类型
 */
public enum TradeEntrustActionEnum {
	BUY(1, 1, "买入"), //买入（app端提交的委托）
	SELL(2, 1, "卖出"), //卖出（app端提交的委托）
	BUY_SPLIT(3, 2, "拆分"), //拆分（买入，web-oms后台手工提交的委托）
	BUY_PRESENT(4, 3, "派送"); //派送（买入，web-oms后台手工提交的委托）
	@Getter
	private final Integer action; //web-oms后台搜索条件
	@Getter
	private final Integer type; //entrust_order表的entrust_type字段
	@Getter
	private final String name; //名称

	TradeEntrustActionEnum(Integer action, Integer type, String name) {
		this.action = action;
		this.type = type;
		this.name = name;
	}

	public static Integer getTypeByAction(Integer action) {
		if (action == null) {
			return null;
		}
		for (TradeEntrustActionEnum c : TradeEntrustActionEnum.values()) {
			if (c.getAction().intValue() == action.intValue()) {
				return c.type;
			}
		}
		return null;
	}

	public static String getNameByAction(Integer action) {
		if (action == null) {
			return null;
		}
		for (TradeEntrustActionEnum v : TradeEntrustActionEnum.values()) {
			if (v.getAction().intValue() == action.intValue()) {
				return v.name;
			}
		}
		return null;
	}

	public static TradeEntrustActionEnum getEntrustAction(String name) {
		if (StringUtils.isBlank(name)) {
			return null;
		}
		name = name.trim();
		for (TradeEntrustActionEnum v : TradeEntrustActionEnum.values()) {
			if (v.getName().equals(name)) {
				return v;
			}
		}
		return null;
	}
}
