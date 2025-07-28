package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;

/**
 * 获取客户限制
 * Created by sunline
 * Date:4/9/16
 * Time:10:12 PM
 */
public class EF01110017VO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String assetProp;
    private String restriction;
    private String fundAccount;
    private String mainFlag;


    public String getAssetProp() {
        return assetProp;
    }

    public void setAssetProp(String assetProp) {
        this.assetProp = assetProp;
    }

    public String getRestriction() {
        return restriction;
    }

    public void setRestriction(String restriction) {
        this.restriction = restriction;
    }

    public String getFundAccount() {
        return fundAccount;
    }

    public void setFundAccount(String fundAccount) {
        this.fundAccount = fundAccount;
    }

    public String getMainFlag() {
        return mainFlag;
    }

    public void setMainFlag(String mainFlag) {
        this.mainFlag = mainFlag;
    }
}
