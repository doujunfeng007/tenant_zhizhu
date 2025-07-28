package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;

/**
 * 恒生功能号：10218
 * 客户基金协议取消
 */
public class EF01110218VO implements Serializable {
    private String serialNo; //int[10] 流水号
    private String opRemark; //char[2000] 操作备注

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getOpRemark() {
        return opRemark;
    }

    public void setOpRemark(String opRemark) {
        this.opRemark = opRemark;
    }
}
