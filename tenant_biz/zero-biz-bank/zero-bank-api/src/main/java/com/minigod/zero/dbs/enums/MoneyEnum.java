package com.minigod.zero.dbs.enums;

/**
 * @ClassName: MoneyEnum
 * @Description:
 * @Author chenyu
 * @Date 2024/3/20
 * @Version 1.0
 */
public enum MoneyEnum {

    SEC_MONEY_TYPE_EN_CNY("CNY","0"),

    SEC_MONEY_TYPE_EN_USD("USD","1"),

    SEC_MONEY_TYPE_EN_HKD("HKD","2"),
            ;

    private final String index;
    private final String name;

    MoneyEnum(String index, String name) {
        this.index = index;
        this.name = name;
    }

    public static String getName(String index) {
        for (MoneyEnum c : MoneyEnum.values()) {
            if (c.getIndex().equals(index)) {
                return c.name;
            }
        }
        return null;
    }

    public static String getIndex(String name) {
        for (MoneyEnum c : MoneyEnum.values()) {
            if (c.getName().equals(name)) {
                return c.getIndex();
            }
        }
        return null;
    }

    public String getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }
}
