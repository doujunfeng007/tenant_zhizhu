package com.minigod.zero.trade.hs.req;

import com.minigod.zero.trade.hs.vo.GrmRequestVO;

import java.io.Serializable;

/**
 * Created by sunline
 * Date:4/9/16
 * Time:10:12 PM
 * 经纪人登录
 */
public class EF01110000Request extends GrmRequestVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String opBranchNo;
    private String opStation;
    private String opEntrustWay;
    private String userType;
    private String branchNo;
    private String opPassword;
    private String operatorNo;
    private String ipAddr;
    private String hostName;

    public String getOpBranchNo() {
        return opBranchNo;
    }

    public void setOpBranchNo(String opBranchNo) {
        this.opBranchNo = opBranchNo;
    }

    public String getOpStation() {
        return opStation;
    }

    public void setOpStation(String opStation) {
        this.opStation = opStation;
    }

    public String getOpEntrustWay() {
        return opEntrustWay;
    }

    public void setOpEntrustWay(String opEntrustWay) {
        this.opEntrustWay = opEntrustWay;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getBranchNo() {
        return branchNo;
    }

    public void setBranchNo(String branchNo) {
        this.branchNo = branchNo;
    }

    public String getOpPassword() {
        return opPassword;
    }

    public void setOpPassword(String opPassword) {
        this.opPassword = opPassword;
    }

    public String getOperatorNo() {
        return operatorNo;
    }

    public void setOperatorNo(String operatorNo) {
        this.operatorNo = operatorNo;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }
}
