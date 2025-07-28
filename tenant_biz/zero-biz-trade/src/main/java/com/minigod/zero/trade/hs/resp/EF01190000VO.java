package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;
import java.util.List;

/**
 * 组合接口查询结果
 */
public class EF01190000VO implements Serializable {
    private static final long serialVersionUID = 1L;

    private EF01110003VO clientCashInfo;//10003接口
    private ClientCashSumInfo clientCashSumInfo;//10004接口
    private List<EF01110005VO> holdingList;//10005接口

    public EF01110003VO getClientCashInfo() {
        return clientCashInfo;
    }

    public void setClientCashInfo(EF01110003VO clientCashInfo) {
        this.clientCashInfo = clientCashInfo;
    }

    public ClientCashSumInfo getClientCashSumInfo() {
        return clientCashSumInfo;
    }

    public void setClientCashSumInfo(ClientCashSumInfo clientCashSumInfo) {
        this.clientCashSumInfo = clientCashSumInfo;
    }

    public List<EF01110005VO> getHoldingList() {
        return holdingList;
    }

    public void setHoldingList(List<EF01110005VO> holdingList) {
        this.holdingList = holdingList;
    }
}
