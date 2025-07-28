package com.minigod.zero.manage.enums;

import lombok.Getter;

/**
 * 模拟比赛的参赛帐号状态
 */
public enum SimulateAccountStateEnum {
    enable(1, "正常"), //正常
    disable(2, "禁用"); //禁用

    @Getter
    private final Integer state; //订单状态
    @Getter
    private final String name; //名称

    SimulateAccountStateEnum(Integer state, String name){
        this.state = state;
        this.name = name;
    }
}
