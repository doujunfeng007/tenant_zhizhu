package com.minigod.zero.trade.hs.req;

import com.minigod.zero.trade.hs.vo.GrmRequestVO;

import java.io.Serializable;

/**
 * 查询历史成交总汇
 * 10149 服务_海外_取历史成交总汇
 */
public class EF01110149Request extends GrmRequestVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String opStation;
    private String fundAccount;
    private String startDate;
    private String endDate;
    private String exchangeType;
//    private String queryDirection = "0";// 查询方向（0：倒序1：正序，默认查询方向为倒序，倒序即比指定定位串小的记录）
    private String isFilter;//是否进行数据过滤

    public String getIsFilter() {
        return isFilter;
    }

    public void setIsFilter(String isFilter) {
        this.isFilter = isFilter;
    }

    public String getOpStation() {
        return opStation;
    }

    public void setOpStation(String opStation) {
        this.opStation = opStation;
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

    public String getFundAccount() {
        return fundAccount;
    }


    public void setFundAccount(String fundAccount) {
        this.fundAccount = fundAccount;
    }

    public String getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(String exchangeType) {
        this.exchangeType = exchangeType;
    }
}
