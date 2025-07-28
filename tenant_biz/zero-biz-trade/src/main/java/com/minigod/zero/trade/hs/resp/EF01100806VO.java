package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;

/**
 * Created by sunline
 * Date:4/9/16
 * Time:10:12 PM
 * 查当日委托
 */
public class EF01100806VO extends EntrustRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    private String cancelable ; //取消
    private String modifiable ; //修改

    private int secSType;
    private String initDate;
    private String conditionType;// 策略类型（0-OQ订单;1-市价到价单;2-限价到价单）
    private String touchPrice;//
    private String strategyType;
    private String strategyEnddate;// 策略失效日期
    private String strategyStatus;// 策略状态（1-未执行;2-执行中;3-执行完成;4-暂停;5-强制释放）

    public String getCancelable() {
        return cancelable;
    }

    public void setCancelable(String cancelable) {
        this.cancelable = cancelable;
    }

    public String getModifiable() {
        return modifiable;
    }

    public void setModifiable(String modifiable) {
        this.modifiable = modifiable;
    }

    public String getStrategyEnddate() {
        return strategyEnddate;
    }

    public void setStrategyEnddate(String strategyEnddate) {
        this.strategyEnddate = strategyEnddate;
    }

    public String getConditionType() {
        return conditionType;
    }

    public void setConditionType(String conditionType) {
        this.conditionType = conditionType;
    }

    public String getStrategyStatus() {
        return strategyStatus;
    }

    public void setStrategyStatus(String strategyStatus) {
        this.strategyStatus = strategyStatus;
    }

    public String getTouchPrice() {
        return touchPrice;
    }

    public void setTouchPrice(String touchPrice) {
        this.touchPrice = touchPrice;
    }

    public String getStrategyType() {
        return strategyType;
    }

    public void setStrategyType(String strategyType) {
        this.strategyType = strategyType;
    }

    public String getInitDate() {
        return initDate;
    }

    public void setInitDate(String initDate) {
        this.initDate = initDate;
    }

    public int getSecSType() {
        return secSType;
    }

    public void setSecSType(int secSType) {
        this.secSType = secSType;
    }
}
