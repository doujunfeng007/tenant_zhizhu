package com.minigod.zero.trade.hs.resp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by sunline on 2016/5/7 16:08.
 * sunline
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EF01100817VO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String moneyType;// char 币种类别(为空查询所有币种,不为空查询该币种资金)
    private String signingFundUncome; //double[19,2] 在途基金
    private String signingFundHold; //double[19,2] 持仓基金
    private String signingFundBalance; //double[19,2] 签约基金合计=在途+持仓
    private String signingFundUncomeS; //double[19,2] 在途申购基金
    private String signingFundUncomeR; //double[19,2] 在途赎回基金

    public String getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(String moneyType) {
        this.moneyType = moneyType;
    }

    public String getSigningFundUncome() {
        return signingFundUncome;
    }

    public void setSigningFundUncome(String signingFundUncome) {
        this.signingFundUncome = signingFundUncome;
    }

    public String getSigningFundHold() {
        return signingFundHold;
    }

    public void setSigningFundHold(String signingFundHold) {
        this.signingFundHold = signingFundHold;
    }

    public String getSigningFundBalance() {
        return signingFundBalance;
    }

    public void setSigningFundBalance(String signingFundBalance) {
        this.signingFundBalance = signingFundBalance;
    }

    public String getSigningFundUncomeS() {
        return signingFundUncomeS;
    }

    public void setSigningFundUncomeS(String signingFundUncomeS) {
        this.signingFundUncomeS = signingFundUncomeS;
    }

    public String getSigningFundUncomeR() {
        return signingFundUncomeR;
    }

    public void setSigningFundUncomeR(String signingFundUncomeR) {
        this.signingFundUncomeR = signingFundUncomeR;
    }
}
