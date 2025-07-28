package com.minigod.zero.biz.common.vo.spiter;

import com.minigod.zero.biz.common.enums.EStkLabelInfo;

public class LabelInfoUrlConfig {
    private EStkLabelInfo eStkLabelInfo;
    private String url;
    private String charset;

    public LabelInfoUrlConfig(EStkLabelInfo industryCode , String url, String charset){
        this.eStkLabelInfo = industryCode;
        this.url = url;
        this.charset = charset;
    }


    public EStkLabelInfo geteStkLabelInfo() {
        return eStkLabelInfo;
    }

    public void seteStkLabelInfo(EStkLabelInfo eStkLabelInfo) {
        this.eStkLabelInfo = eStkLabelInfo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }
}
