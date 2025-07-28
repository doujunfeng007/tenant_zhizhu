package com.minigod.zero.trade.enums;

import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.List;

/**
 * 交易订单状态
 * 智珠APP状态：Y：通过，N：等待，REJ：拒绝
 */
public enum HsFundOrderStatus {


    //"处理中" = 0, @"等待中" = 1, @"排队中" = 2, @"等待取消" = 3, @"部成待撤" = 4, @"部分取消" = 5, @"已取消" = 6,
    // @"部分成交" = 7, @"已成交" = 8, @"已拒绝" = 9, @"已报待改" = A


    //智珠APP原有状态：
    NEW("NEW", "0"), //zszz:未报,
    WA("WA", "1"),   //zszz:待报
    PRO("PRO", "2"), //zszz:已报
    PRO_A("PRO", "A"), //zszz:已报待改
    Q("Q", "未确定"),     //zszz:Queued (see order_sub_status for detail)
    REJECT("REJ", "9"),  //zszz:废单
    REJECT_NEW("REJ", "9"),  //polling: [810025][Its Not Allowed To Do The CancelReplace Order In The Cur
    REJECT_OVERRIDE("REJ", "9"),  //polling:
    REJECT_CANCEL("REJ", "9"),  //polling:
    APPROVAL_REJECTED("REJ", "9"),  //zszz:废单
    PARTIALLY_FILLED("PEX", "7"), //zszz:部分成交
    FILLED("FEX", "8"),  //zszz:已成交
    CANCEL("CAN", "6"),  //zszz:已取消
    CANCEL_WA("CAN_WA", "3"),  //zszz:等待取消
    CANCEL_PEX("CAN_PEX", "4"),  //zszz:部成待撤
    CANCEL_FEX("CAN_FEX", "5"),  //zszz:部分取消

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

    HsFundOrderStatus(String zszz, String polling) {
        this.polling = polling;
        this.zszz = zszz;
    }

    public static String getZszzByHs(String status) {
        for (HsFundOrderStatus type : values()) {
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
    public static boolean isEditable(String status) {
        return editableStatus.contains(status) ;
    }
}
