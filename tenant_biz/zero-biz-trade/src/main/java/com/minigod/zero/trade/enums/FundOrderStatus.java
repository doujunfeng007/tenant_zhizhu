package com.minigod.zero.trade.enums;

import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.List;

/**
 * 交易订单状态
 * 智珠APP状态：Y：通过，N：等待，REJ：拒绝
 */
public enum FundOrderStatus {
    //智珠APP原有状态：
    NEW("NEW", "未确定"), //zszz:未报,
    WA("WA", "Waiting"),   //zszz:待报
    PRO("PRO", "In Market"), //zszz:已报
    Q("Q", "未确定"),     //zszz:Queued (see order_sub_status for detail)
    REJECT("REJ", "Reject"),  //zszz:废单
    REJECT_NEW("REJ", "Reject New"),  //polling: [810025][Its Not Allowed To Do The CancelReplace Order In The Cur
    REJECT_OVERRIDE("REJ", "Override Rejected"),  //polling:
    REJECT_CANCEL("REJ", "Market Close Cancel"),  //polling:
    APPROVAL_REJECTED("REJ", "Approval Rejected"),  //zszz:废单
    PARTIALLY_FILLED("PEX", "Partially Filled"), //zszz:部分成交
    FILLED("FEX", "Filled"),  //zszz:已成交
    CANCEL("CAN", "Cancel"),  //zszz:已取消

    //polling里未准确匹配的状态：
    APPROVAL_REQUIRED("NEW", "Approval Required"),  //zszz:
    APPROVING("NEW", "Approving"),   //zszz:
    OVERRIDING("WA", "Overriding"), //zszz:
    OVERRIDE_SUCCESS("PRO", "Override Success"),  //zszz:
    OVERRIDE_FAIL("REJ", "Override Fail"),  //zszz:
    OVERRIDE("REJ", "Override"),  //zszz:
    ;
    @Getter
    private final String zszz;
    @Getter
    private final String polling;

    FundOrderStatus(String zszz, String polling) {
        this.polling = polling;
        this.zszz = zszz;
    }

    public static String getZszzByIGateway(String status) {
        for (FundOrderStatus type : values()) {
            if (type.polling.equals(status))
                return type.zszz;
        }
        return null;
    }

    /**
     * 可改单或撤单的状态
     */
    private static final List<String> editableStatus = Lists.newArrayList(
            WA.polling, PRO.polling, PARTIALLY_FILLED.polling
    );

    /**
     * 可改单或撤单的状态（OSQTY > 0）
     */
    private static final List<String> editableStatusByOSQTY = Lists.newArrayList(
            APPROVAL_REJECTED.polling, OVERRIDE_SUCCESS.polling, OVERRIDE_FAIL.polling, REJECT.polling, REJECT_NEW.polling
    );

    /**
     * 是否可改单或撤单
     *
     * @param status 状态
     * @param osqty  流通量
     * @return boolean
     */
    public static boolean isEditable(String status, double osqty) {
        return editableStatus.contains(status) || osqty > 0 && editableStatusByOSQTY.contains(status);
    }
}
