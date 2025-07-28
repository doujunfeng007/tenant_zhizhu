package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;

/**
 * Created by sunline
 * Date:4/9/16
 * Time:10:12 PM
 * 取最大可买卖股票数量
 */
public class EF01100301VO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String enableAmount;

    private String cashAmount;

    public String getEnableAmount() {
        return enableAmount;
    }

    public void setEnableAmount(String enableAmount) {
        this.enableAmount = enableAmount;
    }

    public String getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(String cashAmount) {
        this.cashAmount = cashAmount;
    }
}
