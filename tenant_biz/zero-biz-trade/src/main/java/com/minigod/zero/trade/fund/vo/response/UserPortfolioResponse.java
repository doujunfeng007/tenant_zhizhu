package com.minigod.zero.trade.fund.vo.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 查询客户持仓
 * @author: Larry Lai
 * @date: 2020/4/14 10:08
 * @version: v1.0
 */

@Data
public class UserPortfolioResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 资产ID
     */
    private String assetId;

    /**
     * 买入均价
     */
    private String avBuyPrice;

    /**
     * 成本价
     */
    private String costPrice;

    /**
     * 当前数量
     */
    private String currentQty;

    /**
     * 可用数量
     */
    private String enableQty;

    /**
     * 股票市场[K-港股 P-美股]
     */
    private String exchangeType;

    /**
     * 盈亏额
     */
    private String incomeBalance;

    /**
     * 盈亏比率
     */
    private String incomeRatio;

    /**
     * 最新价
     */
    private String lastPrice;

    /**
     * 市值
     */
    private String marketValue;

    /**
     * 币种[HKD-港币 USD-美元 CNY-人民币]
     */
    private String currency;

    /**
     * 股票代码
     */
    private String stockCode;

    /**
     * 股票名称
     */
    private String stockName;

    /**
     * 股票二级子类
     */
    private String secSType;
}
