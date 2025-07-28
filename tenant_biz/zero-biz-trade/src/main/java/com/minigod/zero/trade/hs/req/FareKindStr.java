package com.minigod.zero.trade.hs.req;

import java.io.Serializable;

/**
 * Created by sunline on 2016/8/25 10:28.
 * sunline
 */
public class FareKindStr implements Serializable{
    private static final long serialVersionUID = 1L;
    private String tradeFareCode;
    private String serviceFareCode;

    public FareKindStr(String tradeFareCode, String serviceFareCode){
        this.tradeFareCode = tradeFareCode;
        this.serviceFareCode = serviceFareCode;
    }

    public String toString(){
        return new StringBuilder().append("000000000000000000000000000000000000000000000000")
                .append(this.serviceFareCode).append(this.tradeFareCode).append("00000000").toString();
    }

    public String getTradeFareCode() {
        return tradeFareCode;
    }

    public void setTradeFareCode(String tradeFareCode) {
        this.tradeFareCode = tradeFareCode;
    }

    public String getServiceFareCode() {
        return serviceFareCode;
    }

    public void setServiceFareCode(String serviceFareCode) {
        this.serviceFareCode = serviceFareCode;
    }
}
