package com.minigod.zero.trade.fund.vo.request;

import com.minigod.zero.trade.vo.request.BaseRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 获取资产汇总信息和持仓列表的请求参数
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FundInfoRequest extends BaseRequest {
    /**
     * 股票市场[K-港股 P-美股]
     */
    private String exchangeType;

    /**
     * 币种[HKD-港币 USD-美元 CNY-人民币]
     */
    private String currency;
}
