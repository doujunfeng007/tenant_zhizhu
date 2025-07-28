package com.minigod.zero.trade.hs.req;

import com.minigod.zero.trade.hs.vo.GrmRequestVO;

import java.io.Serializable;

/**
 * Created by sunline
 * Date:4/9/16
 * Time:10:12 PM
 * 取客户帐号列表
 */
public class EF01100814Request extends GrmRequestVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String actionIn;
    private String fromMoneyType;
    private String toMoneyType;

    public String getActionIn() {
        return actionIn;
    }

    public void setActionIn(String actionIn) {
        this.actionIn = actionIn;
    }

    public String getFromMoneyType() {
        return fromMoneyType;
    }

    public void setFromMoneyType(String fromMoneyType) {
        this.fromMoneyType = fromMoneyType;
    }

    public String getToMoneyType() {
        return toMoneyType;
    }

    public void setToMoneyType(String toMoneyType) {
        this.toMoneyType = toMoneyType;
    }
}
