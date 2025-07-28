package com.minigod.zero.trade.fund.vo.request;

import com.minigod.zero.trade.vo.request.BaseRequest;
import lombok.Data;

/**
 * @description: 查询历史委托
 * @author: Larry Lai
 * @date: 2020/4/13 15:19
 * @version: v1.0
 */

@Data
public class HistoryOrdersRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 股票市场[HK-港股 US-美股]
     */
    private String exchangeType;

    /**
     * 开始日期 yyyy-MM-dd
     */
    private String startDate;

    /**
     * 结束日期 yyyy-MM-dd
     */
    private String endDate;

	/**
	 * 股票code  腾讯 = 700
	 */
	private String stockCode;

	/**
	 * 条件单状态 C0.待触发,C1.已取消(撤单),C2.已失效,C3.已触发
	 */
	private String entrustStatus;


}
