package com.minigod.zero.trade.sjmb.req.brokerapi;

import lombok.Data;

import java.io.Serializable;

/**
 * @author chen
 * @ClassName PositionFlowReq.java
 * @Description TODO
 * @createTime 2024年01月13日 10:14:00
 */
@Data
public class PositionFlowReq implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 泰坦系统使用的账号ID
     */
    private String accountId;
    /**
     * 业务起始日期
     */
    private String beginDate;
    /**
     * 业务截止日期
     */
    private String endDate;
    /**
     * 交易所
     */
    private String exchange;
    /**
     * 一页的条目数量，默认为10
     */
    private Integer limit;
    /**
     * 市场
     */
    private String market;
    /**
     * 第?页，默认为1
     */
    private Integer page;
    /**
     * 证券类型
     */
    private String securityType;
    /**
     * 资金组
     */
    private String segmentId;
    /**
     * 标的代码
     */
    private String symbol;
    /**
     * 持仓流水子分类，参考返回值中的transSubType枚举类型
     */
    private String transSubType;
    /**
     * 持仓流水分类，参考返回值中的transType枚举类型
     */
    private String transType;
}
