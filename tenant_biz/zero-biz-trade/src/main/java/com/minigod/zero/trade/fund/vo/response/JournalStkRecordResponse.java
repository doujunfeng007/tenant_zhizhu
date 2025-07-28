package com.minigod.zero.trade.fund.vo.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 查询当日股票流水
 * @author: Larry Lai
 * @date: 2020/4/14 10:08
 * @version: v1.0
 */

@Data
public class JournalStkRecordResponse implements Serializable {

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
     * 资产ID
     */
    private String assetId;

    /**
     * 股票代码
     */
    private String stockCode;

    /**
     * 股票名称
     */
    private String stockName;

    /**
     * 数量
     */
    private String amount;

    /**
     * 剩余数量
     */
    private String postAmount;

    /**
     * 股票市场[K-港股 P-美股]
     */
    private String exchangeType;

    /**
     * 成交均价
     */
    private String averagePrice;

}
