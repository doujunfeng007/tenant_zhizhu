package com.minigod.zero.cust.constant;

import cn.hutool.core.collection.CollectionUtil;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 币种
 *
 * @author eric
 * @since 2024-05-22 22:18:09
 */
public enum MoneyTypeEnum {
	CNY("0", "CNY"),
	USD("1", "USD"),
	HKD("2", "HKD");

	public String value;
	public String description;

	public String getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}

	MoneyTypeEnum(String value, String description) {
		this.value = value;
		this.description = description;
	}

	public static MoneyTypeEnum getByCode(String value) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		List<MoneyTypeEnum> collect = Arrays.stream(values()).filter(o -> o.getValue().equals(value)).collect(Collectors.toList());
		if (CollectionUtil.isNotEmpty(collect)) {
			return collect.get(0);
		}
		return null;
	}

	public static String getDesc(String value) {
		MoneyTypeEnum anEnum = getByCode(value);
		if (anEnum == null) {
			return null;
		} else {
			return anEnum.getDescription();
		}
	}
}
