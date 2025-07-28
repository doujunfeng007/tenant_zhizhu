package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;

/**
 * 343 策略撤单
 */
public class EF01100343VO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String retCode;//成功标记,返回1表示撤单成功

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }
}
