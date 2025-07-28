package com.minigod.zero.trade.sjmb.resp.brokerapi;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author chen
 * @ClassName CashFlowResp.java
 * @Description TODO
 * @createTime 2024年01月12日 16:25:00
 */
@Data
public class CashFlowResp implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 具体数据
     */
    private List<CashFlow> items;
    /**
     * 一页的条目数量
     */
    private long limit;
    /**
     * 第?页
     */
    private long page;
    /**
     * 条目总数量
     */
    private long total;

    @Data
    public static class CashFlow implements Serializable{

        private static final long serialVersionUID = 1L;

        /**
         * 账号，泰坦系统使用的账号ID
         */
        private String accountId;
        /**
         * 金额
         */
        private BigDecimal amount;
        /**
         * 流水发生日期，格式：yyyy/MM/dd；如：2021/07/23
         */
        private String businessDate;
        /**
         * 流水编号
         */
        private String cashFlowId;
        /**
         * 币种
         */
        private String currency;
        /**
         * 费用名目，只有监管费才有该字段
         */
        private String feeItem;
        /**
         * 流水所关联的业务编号
         */
        private String refId;
        /**
         * 资金组
         */
        private String segmentId;
        /**
         * 合约代码
         */
        private String symbol;
        /**
         * 资金流水子分类
         */
        private String transSubType;
        /**
         * 资金流水分类
         */
        private String transType;
        /**
         * 流水最近变动时间，毫秒
         */
        private Long updateAt;
    }
}
