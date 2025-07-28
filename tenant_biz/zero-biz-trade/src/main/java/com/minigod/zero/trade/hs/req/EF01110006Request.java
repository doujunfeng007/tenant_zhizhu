package com.minigod.zero.trade.hs.req;

import com.minigod.zero.trade.hs.vo.GrmRequestVO;

import java.io.Serializable;

/**
 * Created by sunline
 * Date:4/9/16
 * Time:10:12 PM
 * 资金冻结
 */
public class EF01110006Request extends GrmRequestVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String fundAccount;
    private String moneyType;
    private String occurBalance;
    private String validDate;
    private String frozen_reason;
    private String remark;
    private String localeRemark;
    private String overdraftForcedFlag;


    public String getFundAccount() {
        return fundAccount;
    }

    public void setFundAccount(String fundAccount) {
        this.fundAccount = fundAccount;
    }

    public String getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(String moneyType) {
        this.moneyType = moneyType;
    }

    public String getOccurBalance() {
        return occurBalance;
    }

    public void setOccurBalance(String occurBalance) {
        this.occurBalance = occurBalance;
    }

    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }

    public String getFrozen_reason() {
        return frozen_reason;
    }

    public void setFrozen_reason(String frozen_reason) {
        this.frozen_reason = frozen_reason;
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

    public String getOverdraftForcedFlag() {
        return overdraftForcedFlag;
    }

    public void setOverdraftForcedFlag(String overdraftForcedFlag) {
        this.overdraftForcedFlag = overdraftForcedFlag;
    }
}
