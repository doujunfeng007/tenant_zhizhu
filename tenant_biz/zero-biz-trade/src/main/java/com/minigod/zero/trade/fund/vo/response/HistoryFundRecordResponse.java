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
public class HistoryFundRecordResponse implements Serializable {

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
}
