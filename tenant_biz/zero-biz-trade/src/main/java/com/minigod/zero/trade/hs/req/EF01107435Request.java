package com.minigod.zero.trade.hs.req;

import com.minigod.zero.trade.hs.vo.GrmRequestVO;

import java.io.Serializable;

/**
 * 7435 取前台历史基金委托
 */
public class EF01107435Request extends GrmRequestVO implements Serializable {
    private String clientId; //int 柜台交易账号
    private String fundAccount; //int 柜台资金账号
    private String password; //char[32] 交易密码
    private String exchangeType; //char[4] 交易类别
//    private String transferFlag; //char 转入标志(不传查全部,0：查没有转入后台的委托，1：查转入后台的委托)
    private String startDate; //int[8] 起始日期（起始日期和结束日期不能都为0，起始日期不能大于结束日期）
    private String endDate; //int[8] 结束日期（起始日期和结束日期不能都为0，起始日期不能大于结束日期）

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getFundAccount() {
        return fundAccount;
    }

    public void setFundAccount(String fundAccount) {
        this.fundAccount = fundAccount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(String exchangeType) {
        this.exchangeType = exchangeType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
