package com.minigod.zero.trade.vo.afe;

/**
 * sunline 订单状态
 */
public enum AfeEntrustStatusEnum {
    /**
     * 订单状态
     */
	NOT_REPORTED_TX("0", "TX"),   //未报
	NOT_REPORTED_PS("0", "PS"),   //未报
    NOT_REPORTED("0", "BR"),   //未报
    TO_BE_REPORTED("1", "QE"), // 待报
    ALREADY_REPORTED("2", "RE"), // 已报
    ALREADY_REPORTED_TO_BE_WITHDRAWN("3", "PENDING_CANCEL"), // 已报待撤
    COMPLETED_TO_BE_WITHDRAWN("4", "4"), // 部成待撤
    PART_WITHDRAWN("5", "5"), // 部撤
    WITHDRAWN("6", "CA"), // 已撤
    PART_COMPLETED("7", "PF"), // 部成
    COMPLETED("8", "FF"), // 已成
    CANCELED_ORDER("9", "EJ"), // 废单
	CANCELED_ORDER_AJ("9", "AJ"), // 废单
	CANCELED_ORDER_BJ("9", "BJ"), // 废单
	CANCELED_ORDER_EX("9", "EX"), // 废单  到期
    ALREADY_REPORTED_WAIT_CHANGED("A", "REPLACED"), // 已报待改
    TO_BE_REVIEWED_WAIT("B", "B"), // 待复核 人手买方待确认
    RECHECKING("C", "C"), // 重新检查中
    TO_BE_TRIGGERED("D", "D"), // 待触发
    PART_COMPLETED_WAIT_CHANGED("E", "E"), // 部成待改
    WITHDRAWN_EXPIRED("F", "F"), // 撤单(过期)
    PART_WITHDRAWN_EXPIRED("G", "G"), // 部撤(过期)
    TO_BE_REVIEWED("H", "PA"), // 待复核
    PENDING("I", "I"), // PENDING
    REVIEWED_NOT_PASS("J", "J"), // 复核未通过
    WAIT_FOR_CHECK("M", "M"), // Wait for check<
    REVIEWED_CHANGED("N", "N"), // 修改待复核
    REVIEWED_CHANGED_PART_COMPLETED("O", "O"), // 修改待复核[部成]
    REVIEWED_CHANGED_NOT_REPORTED("P", "P"), // 修改待复核 未报
    CONFIRMING("R", "R"), // 确认中 人手单
    PENDING_PARTIALLY_FILL("U", "U"), // Pending[Partially Fill]
    TO_BE_CONFIRMING("W", "W"), // 待确认
    PRE_FILLED("X", "X"); // Pre Filled

    private String entrustStatus;
    private String counterEntrustStatus;

    AfeEntrustStatusEnum(String entrustStatus, String counterEntrustStatus) {
        this.entrustStatus = entrustStatus;
        this.counterEntrustStatus = counterEntrustStatus;
    }

    public static String getCounterEntrustStatus(String entrustStatus) {
        for (AfeEntrustStatusEnum i : AfeEntrustStatusEnum.values()) {
            if (i.getEntrustStatus().equals(entrustStatus)) {
                return i.getCounterEntrustStatus();
            }
        }
        return null;
    }

    public static String getZsEntrustStatus(String hsEntrustStatus) {
        for (AfeEntrustStatusEnum i : AfeEntrustStatusEnum.values()) {
            if (i.getCounterEntrustStatus().equals(hsEntrustStatus)) {
                return i.getEntrustStatus();
            }
        }
        return null;
    }



    public  String getEntrustStatus() {
        return entrustStatus;
    }

    public String getCounterEntrustStatus() {
        return counterEntrustStatus;
    }

}
