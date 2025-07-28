package com.minigod.zero.trade.hs.req;

import com.minigod.zero.trade.hs.vo.GrmRequestVO;

import java.io.Serializable;

/**
 * Created by sunline
 * Date:4/9/16
 * Time:10:12 PM
 * 交易费用设置
 */
public class EF01280126Request extends GrmRequestVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String clientId;
    private FareKindStr fareKindStr;
    //可选，起始时间
    private String startDate;
    //可选，截止时间
    private String endDate;
    //智珠ID
    private String yyId;
    //备注原因
    private String remark;

    public FareKindStr getFareKindStr() {
        return fareKindStr;
    }

    public void setFareKindStr(FareKindStr fareKindStr) {
        this.fareKindStr = fareKindStr;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
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

    public String getYyId() {
        return yyId;
    }

    public void setYyId(String yyId) {
        this.yyId = yyId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
