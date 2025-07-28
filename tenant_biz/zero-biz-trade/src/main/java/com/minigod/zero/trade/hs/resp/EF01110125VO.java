package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;

/**
 * 客户资料查询
 * Created by sunline
 * Date:4/9/16
 * Time:10:12 PM
 */
public class EF01110125VO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String brithday;
    private String clientName;
    private String idKind;
    private String idNo;
    private String idBegindate;
    private String idTerm;
    private String assetProp;
    private String brokerAccount;

    public String getBrithday() {
        return brithday;
    }

    public void setBrithday(String brithday) {
        this.brithday = brithday;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getIdKind() {
        return idKind;
    }

    public void setIdKind(String idKind) {
        this.idKind = idKind;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getIdBegindate() {
        return idBegindate;
    }

    public void setIdBegindate(String idBegindate) {
        this.idBegindate = idBegindate;
    }

    public String getIdTerm() {
        return idTerm;
    }

    public void setIdTerm(String idTerm) {
        this.idTerm = idTerm;
    }

    public String getAssetProp() {
        return assetProp;
    }

    public void setAssetProp(String assetProp) {
        this.assetProp = assetProp;
    }

    public String getBrokerAccount() {
        return brokerAccount;
    }

    public void setBrokerAccount(String brokerAccount) {
        this.brokerAccount = brokerAccount;
    }
}
