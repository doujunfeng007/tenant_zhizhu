package com.minigod.zero.manage.enums;

import lombok.Getter;

/**
 * 模拟比赛参赛参赛者过滤类型
 */
public enum CompetitionFilterTypeEnum {
    NONE(0), //无限制
    WHITE_LIST(1), //白名单
    BLACK_LIST(2); //黑名单

    @Getter
    private final int type;

    CompetitionFilterTypeEnum(int status) {
        this.type = status;
    }
}
