package com.minigod.zero.flow.core.common.enums;

/**
 * 流程意见类型
 *
 * @author zsdp
 * @date 2021/4/19
 */
public enum FlowComment {

    /**
     * 说明
     */
    NORMAL("1", "通过"),
    REBACK("2", "退回"),
    REBACK_0("8", "退回至基础资料审核"),
    REJECT("3", "驳回"),
    DELEGATE("4", "委派"),
    TRANSFER("5", "转办"),
    STOP("6", "终止"),
    EXCEPTION("7", "异常"),
    FORBID("9", "禁止开户"),
    ALLOW("10", "允许开户"),
    ;

    /**
     * 类型
     */
    private final String type;

    /**
     * 说明
     */
    private final String remark;

    FlowComment(String type, String remark) {
        this.type = type;
        this.remark = remark;
    }

    public String getType() {
        return type;
    }

    public String getRemark() {
        return remark;
    }
}
