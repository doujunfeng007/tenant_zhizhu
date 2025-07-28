package com.minigod.zero.trade.hs.req;

import com.minigod.zero.trade.hs.vo.GrmRequestVO;

import java.io.Serializable;

/**
 * Created by sunline
 * Date:4/9/16
 * Time:10:12 PM
 * AE/操作员退出
 */
public class EF01110019Request extends GrmRequestVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String opPassword;

    public String getOpPassword() {
        return opPassword;
    }

    public void setOpPassword(String opPassword) {
        this.opPassword = opPassword;
    }
}
