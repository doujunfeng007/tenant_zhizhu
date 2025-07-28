package com.minigod.zero.manage.enums;

import lombok.Getter;

/**
 * 模拟交易订单状态
 */
public enum TradeOrderStateEnum {
    waiting(1, "未成交"), //买入（app端提交的委托）
    fill(2, "已成交"), //卖出（app端提交的委托）
    cancel(3,  "已撤单"); //拆分（买入，web-oms后台手工提交的委托）

    @Getter
    private final Integer state; //订单状态
    @Getter
    private final String name; //名称

    TradeOrderStateEnum(Integer state, String name){
        this.state = state;
        this.name = name;
    }

    public static String getNameByState(Integer state) {
        if(state == null){
            return null;
        }
        for (TradeOrderStateEnum c : TradeOrderStateEnum.values()) {
            if (c.getState().intValue() == state.intValue()) {
                return c.name;
            }
        }
        return null;
    }
}
