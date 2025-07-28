package com.minigod.zero.trade.fund.vo.request;

import com.minigod.zero.trade.vo.request.BaseRequest;
import lombok.Data;

/**
 * @description: 查询当日委托
 * @author: Larry Lai
 * @date: 2020/4/13 15:19
 * @version: v1.0
 */

@Data
public class JournalOrdersRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private String exchangeType;

	/**
	 * 订单状态
	 */
	private String orderStatus;

	private String nextToId;

	private Long limit;

}
