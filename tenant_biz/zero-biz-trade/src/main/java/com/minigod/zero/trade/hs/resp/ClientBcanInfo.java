package com.minigod.zero.trade.hs.resp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * @author sunline
 * @date 2020/7/30
 * 客户bcan码信息
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientBcanInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /*private String ClientId;
    private String firstName;
    private String lastName;
    private String englishName;
    private String chineseName;
    private String clientType;
    private String idType;
    private String idNo;*/
    private String bcan;
    private String status;
    private String errorInfo;

    public String getBcan() {
        return bcan;
    }

    public void setBcan(String bcan) {
        this.bcan = bcan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }
}
