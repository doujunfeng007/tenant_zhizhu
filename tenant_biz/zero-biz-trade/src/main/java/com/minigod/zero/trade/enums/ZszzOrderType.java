package com.minigod.zero.trade.enums;

/**
 * 智珠app端 订单类型
 */
public enum ZszzOrderType {
    AUCTION_LIMIT("I","g"), //竞价限价盘
    AT_AUCTION("A","d"), //竞价盘
    LIMIT("L","h"), //限价盘
    ENHANCED_LIMIT_ORDER("E","e"), //增强限价盘
    SPECIAL_LIMIT_ORDER("S","j"), //特别限价盘
    GREY_MARKET_ORDER("U",""), //暗盘
    ;
    private final String type;
    private final String hsType;

    ZszzOrderType(String type,String hsType) {
        this.type = type;
        this.hsType = hsType;
    }

    public String value() {
        return type;
    }
}
