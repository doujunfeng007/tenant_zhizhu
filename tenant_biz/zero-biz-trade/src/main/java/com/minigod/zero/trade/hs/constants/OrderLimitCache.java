package com.minigod.zero.trade.hs.constants;

import java.io.Serializable;

public class OrderLimitCache implements Serializable {
    private String tradeAccount;
    private String fundAccount;

    public String getTradeAccount() {
        return tradeAccount;
    }

    public void setTradeAccount(String tradeAccount) {
        this.tradeAccount = tradeAccount;
    }

    public String getFundAccount() {
        return fundAccount;
    }

    public void setFundAccount(String fundAccount) {
        this.fundAccount = fundAccount;
    }
}
