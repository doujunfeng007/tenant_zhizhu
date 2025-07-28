package com.minigod.zero.trade.fund.vo.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 查询客户资金汇总
 * @author: Larry Lai
 * @date: 2020/4/14 10:08
 * @version: v1.0
 */

@Data
public class TotalAccountResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 总资产
     */
    private String asset;

    /**
     * 综合购买力
     */
    private String enableBalance;

    /**
     * 冻结资金
     */
    private String frozenBalance;

    /**
     * 总市值
     */
    private String marketValue;

    /**
     * ipo购买力
     */
    private String ipoBalance;

    /**
     * 可取资金
     */
    private String fetchBalance;

    /**
     * T日可取资金
     */
    private String fetchBalanceT;

    /**
     * IPO在途
     */
    private String ipoApplyingBalance;

    /**
     * 交易额度
     */
    private String creditLine;

    /**
     * 抵押市值
     */
    private String marginValue;

    /**
     * 抵押额度/信用
     */
    private String maxExposure;

    /**
     * 账面余额
     */
    private String LedgerBalance;
}
