package com.minigod.zero.platform.enums;

public enum EmailEventStatus {
    // 请求(request) 邮件请求成功
    REQUEST("request", "邮件请求成功"),
    DELIVER("deliver", "邮件发送成功"),
    //OPEN("open", "用户打开邮件"),
    //CLICK("click", "用户点击链接"),
    UNSUBSCRIBE("unsubscribe", "用户取消订阅邮件"),
    //REPORT_SPAM("report_spam", "用户举报邮件"),
    INVALID("invalid", "邮件未发送成功"),
    SOFT_BOUNCE("soft_bounce", "接收方拒收该邮件"),
    ROUTE("route", "转信/收信路由");

    private final String code; // 事件代码
    private final String description; // 事件描述

    EmailEventStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code; // 获取事件代码
    }

    public String getDescription() {
        return description; // 获取事件描述
    }
}
