package com.minigod.zero.trade.tiger.req;


import lombok.Data;

import java.util.List;

@Data
public class TradeTransactionsReq extends TigerBaseRequest{

    /**
     * 资金账户列表，如果不传则查询当前大账号下的交易流水
     */
    private List<Long> accountIds;
    /**
     * 合约ID
     */
    private String contractId;
    /**
     * 到期日
     */
    private String expiry;

    /**
     * limit / 限制 limit <= 100
     */
    private Long limit;

    /**
     * 市场
     */
    private String market;

    /**
     * 关联id
     */
    private List<String> refIds;

    /**
     * 关联类型
     */
    private String refType;

    private String right;

    private String secType;

    /**
     * 交易开始时间
     */
    private Long sinceTime;

    private String strike;

    private String symbol;

    private Long toId;

    /**
     * transaction to time / 交易结束时间
     */
    private Long toTime;

    /**
     * 交易日，格式yyyy-MM-dd
     */
    private String tradeDate;


}
