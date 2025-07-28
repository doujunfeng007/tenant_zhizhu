package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunline on 2016/5/7 15:47.
 * sunline
 */
public class FundAccountInfoWithCash extends FundAccountInfo implements Serializable{
    private static final long serialVersionUID = 1L;
    private List<StockAccountInfo> stkAccts;
    private List<EF01110003VO> cashInfos;
    private ClientCashSumInfo cashSumInfo;

    public List<StockAccountInfo> getStkAccts() {
        return stkAccts;
    }

    public void setStkAccts(List<StockAccountInfo> stkAccts) {
        this.stkAccts = stkAccts;
    }

    public List<EF01110003VO> getCashInfos() {
        return cashInfos;
    }

    public void setCashInfos(List<EF01110003VO> cashInfos) {
        this.cashInfos = cashInfos;
    }

    public ClientCashSumInfo getCashSumInfo() {
        return cashSumInfo;
    }

    public void setCashSumInfo(ClientCashSumInfo cashSumInfo) {

        this.cashSumInfo = cashSumInfo;
    }

}
