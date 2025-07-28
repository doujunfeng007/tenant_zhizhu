/**
 * @Title: EF01110180Request.java
 * @Description: TODO
 * @author yanghu.luo
 * @date 2022年8月11日
 */

package com.minigod.zero.trade.hs.req;

import com.minigod.zero.trade.hs.vo.GrmRequestVO;

import java.io.Serializable;

/**
 * @ClassName: EF01110180Request
 * @Description: TODO
 * @author yanghu.luo
 * @date 2022年8月11日
*/

public class EF01110180Request extends GrmRequestVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String fundAccount;
    private String moneyType;


    public String getFundAccount() {
        return fundAccount;
    }

    public void setFundAccount(String fundAccount) {
        this.fundAccount = fundAccount;
    }

    public String getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(String moneyType) {
        this.moneyType = moneyType;
    }
}
