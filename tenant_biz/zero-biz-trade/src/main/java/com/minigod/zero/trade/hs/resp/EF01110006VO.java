package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;

/**
 * Created by sunline
 * Date:4/9/16
 * Time:10:12 PM
 * 资金冻结
 */
public class EF01110006VO   implements Serializable {
    private static final long serialVersionUID = 1L;
    private String serialNo;
    private String revertSerialNo;
    private String initDate;

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getRevertSerialNo() {
        return revertSerialNo;
    }

    public void setRevertSerialNo(String revertSerialNo) {
        this.revertSerialNo = revertSerialNo;
    }

    public String getInitDate() {
        return initDate;
    }

    public void setInitDate(String initDate) {
        this.initDate = initDate;
    }
}
