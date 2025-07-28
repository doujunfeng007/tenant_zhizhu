package com.minigod.zero.trade.fund.vo.request;

import com.minigod.zero.trade.vo.request.BaseRequest;
import lombok.Data;

/**
 * @description: 现金取出
 * @author: Larry Lai
 * @date: 2020/5/6 11:24
 * @version: v1.0
 */

@Data
public class CashWithdrawRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 币种[HKD-港币 USD-美金 CNY-人民币]
     */
    private String currency;

    /**
     * 金额
     */
    private String amount;

    /**
     * 交易备注
     */
    private String remark;

    /**
     * 是否货币转账[Y-是 N-否]
     */
    private String fxTransfer;

    /**
     * 银行代码
     */
    private String bankCode;

    /**
     * 银行账号
     */
    private String bankAcc;

}
