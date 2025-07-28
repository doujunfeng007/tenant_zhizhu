package com.minigod.zero.trade.enums;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 货币类型
 */
public enum CurrencyType {
    CNY,
    HKD,
    USD,
    SGD,
    EUR,
    ;

    public static CurrencyType get(String currency) {
        for (CurrencyType type : values()) {
            if (type.name().equals(currency))
                return type;
        }
        return null;
    }

    /**
     * 货币列表
     */
    protected static final List<String> currencyList = Stream.of(values()).map(Enum::name).collect(Collectors.toList());
}
