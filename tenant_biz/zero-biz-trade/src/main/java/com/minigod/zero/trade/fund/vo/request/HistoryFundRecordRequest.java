package com.minigod.zero.trade.fund.vo.request;

import com.minigod.zero.trade.vo.request.BaseRequest;
import lombok.Data;

/**
 * @description: 查询历史资金流水
 * @author: Larry Lai
 * @date: 2020/4/13 15:19
 * @version: v1.0
 */

@Data
public class HistoryFundRecordRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 币种[HKD-港币 USD-美金 CNY-人民币]
     */
    private String currency;

    /**
     * 开始日期 yyyy-MM-dd
     */
    private String startDate;

    /**
     * 结束日期 yyyy-MM-dd
     */
    private String endDate;

    /**
     * 现金存取：cash_io，股票买卖：trade_io, 为空查询所有
     * */
    private String type;
}
