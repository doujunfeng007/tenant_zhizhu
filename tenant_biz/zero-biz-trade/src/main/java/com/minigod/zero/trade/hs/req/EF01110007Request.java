package com.minigod.zero.trade.hs.req;

import com.minigod.zero.trade.hs.vo.GrmRequestVO;

import java.io.Serializable;

/**
 * Created by sunline
 * Date:4/9/16
 * Time:10:12 PM
 * 资金解冻
 */
public class EF01110007Request extends GrmRequestVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String jourDate;
    private String jourSerialNo;
    private String cancelBalance;
    private String occurBalance;
    private String remark;
    private String localeRemark;

    public String getJourDate() {
        return jourDate;
    }

    public void setJourDate(String jourDate) {
        this.jourDate = jourDate;
    }

    public String getJourSerialNo() {
        return jourSerialNo;
    }

    public void setJourSerialNo(String jourSerialNo) {
        this.jourSerialNo = jourSerialNo;
    }

    public String getCancelBalance() {
        return cancelBalance;
    }

    public void setCancelBalance(String cancelBalance) {
        this.cancelBalance = cancelBalance;
    }

    public String getOccurBalance() {
        return occurBalance;
    }

    public void setOccurBalance(String occurBalance) {
        this.occurBalance = occurBalance;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLocaleRemark() {
        return localeRemark;
    }

    public void setLocaleRemark(String localeRemark) {
        this.localeRemark = localeRemark;
    }
}
