package com.minigod.zero.trade.hs.req;

import com.minigod.zero.trade.hs.vo.GrmRequestVO;

import java.io.Serializable;

/**
 * 查询客户的汇总币种信息(单客户)
 */
public class EF01110109Request extends GrmRequestVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String opStation; //char[64] 站点/电话
    private String fundAccount; //int[10] 柜台资金账号
    private String toMoneyType; //char 币种类别
    private String entrustWay; //char 委托方式（不传默认取委托方式是4的可用和可取金额组合字段，一般传与op_entrust_way一样）
    private String initDate; //int[8] 初始化或者传入日期（为空，取系统初始化日期）
    private String exchangeType; //char[4] 市场类型（不传计算所有市场的抵押市值，如果传某个市场，会结合681508这个配置项）

    public String getOpStation() {
        return opStation;
    }

    public void setOpStation(String opStation) {
        this.opStation = opStation;
    }

    public String getFundAccount() {
        return fundAccount;
    }

    public void setFundAccount(String fundAccount) {
        this.fundAccount = fundAccount;
    }

    public String getToMoneyType() {
        return toMoneyType;
    }

    public void setToMoneyType(String toMoneyType) {
        this.toMoneyType = toMoneyType;
    }

    public String getEntrustWay() {
        return entrustWay;
    }

    public void setEntrustWay(String entrustWay) {
        this.entrustWay = entrustWay;
    }

    public String getInitDate() {
        return initDate;
    }

    public void setInitDate(String initDate) {
        this.initDate = initDate;
    }

    public String getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(String exchangeType) {
        this.exchangeType = exchangeType;
    }
}
