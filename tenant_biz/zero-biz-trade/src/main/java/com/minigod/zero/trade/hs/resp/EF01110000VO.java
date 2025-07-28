package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;

/**
 * Created by sunline
 * Date:4/9/16
 * Time:10:12 PM
 * 交易登录返回对象
 */
public class EF01110000VO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String sessionId;
    private String mainBranchNo;
    private String defMoneyType;
    private String operatorName;
    private String companyName;
    private String initDate;
    private String sysStatus;
    private String sysStatusName;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getMainBranchNo() {
        return mainBranchNo;
    }

    public void setMainBranchNo(String mainBranchNo) {
        this.mainBranchNo = mainBranchNo;
    }

    public String getDefMoneyType() {
        return defMoneyType;
    }

    public void setDefMoneyType(String defMoneyType) {
        this.defMoneyType = defMoneyType;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getInitDate() {
        return initDate;
    }

    public void setInitDate(String initDate) {
        this.initDate = initDate;
    }

    public String getSysStatus() {
        return sysStatus;
    }

    public void setSysStatus(String sysStatus) {
        this.sysStatus = sysStatus;
    }

    public String getSysStatusName() {
        return sysStatusName;
    }

    public void setSysStatusName(String sysStatusName) {
        this.sysStatusName = sysStatusName;
    }
}
