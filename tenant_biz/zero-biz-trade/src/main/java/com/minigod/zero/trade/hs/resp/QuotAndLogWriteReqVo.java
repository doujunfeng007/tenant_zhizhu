package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;
import java.util.List;

/**
 * Desc:查询行情并且写入日志
 * author:sunline
 * Date:2020/5/13 13:39
 */
public class QuotAndLogWriteReqVo implements Serializable {

    private List<String> assetIds;

    private String mktCode;
    private Long userId;
    private String ip;
    private String sessionId;

    /**
     * 是否延时行情
     */
    private Boolean isDealy = false;

    /**
     * 是否自动判断美股盘前、盘中、盘后行情
     */
    private Boolean isUsAutoChange = false;


    public List<String> getAssetIds() {
        return assetIds;
    }

    public void setAssetIds(List<String> assetIds) {
        this.assetIds = assetIds;
    }

    public String getMktCode() {
        return mktCode;
    }

    public void setMktCode(String mktCode) {
        this.mktCode = mktCode;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Boolean getDealy() {
        return isDealy;
    }

    public void setDealy(Boolean dealy) {
        isDealy = dealy;
    }

    public Boolean getUsAutoChange() {
        return isUsAutoChange;
    }

    public void setUsAutoChange(Boolean usAutoChange) {
        isUsAutoChange = usAutoChange;
    }
}
