
package com.minigod.zero.biz.common.vo.json;

import com.alibaba.fastjson2.annotation.JSONField;

public class PlyVO {

    @JSONField(name = "data")
    private Data mData;
    @JSONField(name="error")
    private String mError;
    @JSONField(name="success")
    private Long mSuccess;

    public Data getData() {
        return mData;
    }

    public void setData(Data data) {
        mData = data;
    }

    public String getError() {
        return mError;
    }

    public void setError(String error) {
        mError = error;
    }

    public Long getSuccess() {
        return mSuccess;
    }

    public void setSuccess(Long success) {
        mSuccess = success;
    }

}
