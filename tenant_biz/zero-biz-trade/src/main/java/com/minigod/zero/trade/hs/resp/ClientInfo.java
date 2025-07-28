package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunline on 2016/5/7 15:45.
 * sunline
 */
public class ClientInfo implements Serializable{
    private static final long serialVersionUID = 1L;
    private String clientId;
    private String branchNo;
    private String clientName;
    private List<FundAccountInfoWithCash> fundAccts;

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

    public List<FundAccountInfoWithCash> getFundAccts() {
        return fundAccts;
    }

    public void setFundAccts(List<FundAccountInfoWithCash> fundAccts) {
        this.fundAccts = fundAccts;
    }

    public String getBranchNo() {
        return branchNo;
    }

    public void setBranchNo(String branchNo) {
        this.branchNo = branchNo;
    }
}
