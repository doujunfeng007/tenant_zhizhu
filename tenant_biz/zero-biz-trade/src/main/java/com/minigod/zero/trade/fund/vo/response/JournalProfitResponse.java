package com.minigod.zero.trade.fund.vo.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 查询当日盈亏
 * @author: Larry Lai
 * @date: 2020/4/14 10:08
 * @version: v1.0
 */

@Data
public class JournalProfitResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 盈亏额
     */
    private String netProfit;

    /**
     * 盈亏比率
     */
    private String netRate;
}
