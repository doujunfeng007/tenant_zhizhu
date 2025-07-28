package com.minigod.zero.biz.common.cache;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class IpoQueueAmt implements Serializable {

    private String assetId;

    private Integer typ;

    private BigDecimal amt;

    private Date nowTime;

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public Integer getTyp() {
        return typ;
    }

    public void setTyp(Integer typ) {
        this.typ = typ;
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public Date getNowTime() {
        return nowTime;
    }

    public void setNowTime(Date nowTime) {
        this.nowTime = nowTime;
    }
}
