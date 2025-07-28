
package com.minigod.zero.biz.common.vo.json;

import com.alibaba.fastjson2.annotation.JSONField;

public class Code {

    @JSONField(name = "ann_type")
    private String mAnnType;
    @JSONField(name = "market_code")
    private String mMarketCode;
    @JSONField(name = "short_name")
    private String mShortName;
    @JSONField(name = "stock_code")
    private String mStockCode;

    public String getAnnType() {
        return mAnnType;
    }

    public void setAnnType(String annType) {
        mAnnType = annType;
    }

    public String getMarketCode() {
        return mMarketCode;
    }

    public void setMarketCode(String marketCode) {
        mMarketCode = marketCode;
    }

    public String getShortName() {
        return mShortName;
    }

    public void setShortName(String shortName) {
        mShortName = shortName;
    }

    public String getStockCode() {
        return mStockCode;
    }

    public void setStockCode(String stockCode) {
        mStockCode = stockCode;
    }

}
