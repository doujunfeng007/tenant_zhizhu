package com.minigod.zero.trade.fund.vo.response;


import lombok.Data;

import java.io.Serializable;

/**
 * Created by @author sunline on 2016/5/7 16:08.
 * sunline  单币种不换汇率
 */
@Data
public class ClientCashInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String currency;
    private String enableBalance;
    private String frozenBalance;
    private String marketValue;
    private String asset;
    private String transferBalance;
    private String fetchBalance = "0.00"; //可取金额
    private String gfFetchBalanceT = "0.00";//T日可用
    private String tradedayBalance;
    private String cashOnHold;
    private String maxExposure;
    private String creditLine;
    private String marginValue;
    private String marginCall;
    private String ipoBalance;
    private String ipoApplyingBalance;
    private String incomeBalance = "0.00";
    private String incomeRatio;
    private String currentBalance;
    private String creditValue;
    private String loanValue;

}
