package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by sunline on 2016/5/7 15:45.
 * Redis存储对象
 * sunline
 */
public class ClientSession implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userType;
    private String clientId;
    private String branchNo;
    private String clientName;
    private String sessionId;
    private boolean hasLogin = false;
    private String sid;
    private String ipAddress;
    private String opStation;
    private String entrustWay;
    private Date loginTime;
    private Date logoutTime;
    //0 :client  1:operator   2:broker
    private Date lastOperateTime;
    private List<FundAccountInfo> fundAccts;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public List<FundAccountInfo> getFundAccts() {
        return fundAccts;
    }

    public void setFundAccts(List<FundAccountInfo> fundAccts) {
        this.fundAccts = fundAccts;
    }

    public String getBranchNo() {
        return branchNo;
    }

    public void setBranchNo(String branchNo) {
        this.branchNo = branchNo;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getOpStation() {
        return opStation;
    }

    public void setOpStation(String opStation) {
        this.opStation = opStation;
    }

    public String getEntrustWay() {
        return entrustWay;
    }

    public void setEntrustWay(String entrustWay) {
        this.entrustWay = entrustWay;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Date getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(Date logoutTime) {
        this.logoutTime = logoutTime;
    }

    public Date getLastOperateTime() {
        return lastOperateTime;
    }

    public void setLastOperateTime(Date lastOperateTime) {
        this.lastOperateTime = lastOperateTime;
    }

    public boolean getHasLogin() {
        return hasLogin;
    }

    public void setHasLogin(boolean hasLogin) {
        this.hasLogin = hasLogin;
    }
}


