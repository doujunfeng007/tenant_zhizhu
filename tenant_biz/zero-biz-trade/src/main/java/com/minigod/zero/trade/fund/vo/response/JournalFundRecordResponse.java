package com.minigod.zero.trade.fund.vo.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 查询当日资金流水
 * @author: Larry Lai
 * @date: 2020/4/14 10:08
 * @version: v1.0
 */

@Data
public class JournalFundRecordResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 流水号
     */
    private String serialNo;

    /**
     * 业务标识
     */
    private String businessFlag;

    /**
     * 业务名称
     */
    private String businessName;

    /**
     * 业务时间
     */
    private String businessTime;

    /**
     * 金额
     */
    private String amount;

    /**
     * 币种[HKD-港币 USD-美金 CNY-人民币]
     */
    private String currency;

    /**
     * 状态[Y-通过 N-等待 REJ-拒绝]
     */
    private String status;

    /**
     * 当前余额
     */
    private String postBalance;

    /**
     * 对手账号
     */
    private String rltAc;
    /**
     * 借贷方向
     */
    private String drcrflg;
}
