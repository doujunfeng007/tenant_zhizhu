package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;

public class EF01100810VO extends EntrustRecord implements Serializable {

    private String initDate;
    private String stockNamegb;
    private String logStatus;

    public String getLogStatus() {
        return logStatus;
    }

    public void setLogStatus(String logStatus) {
        this.logStatus = logStatus;
    }

    public String getInitDate() {
        return initDate;
    }

    public void setInitDate(String initDate) {
        this.initDate = initDate;
    }

    public String getStockNamegb() {
        return stockNamegb;
    }

    public void setStockNamegb(String stockNamegb) {
        this.stockNamegb = stockNamegb;
    }
}
