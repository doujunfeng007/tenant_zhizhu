package com.minigod.zero.trade.fund.vo.request;

import com.minigod.zero.trade.vo.request.BaseRequest;
import lombok.Data;

/**
 * @author:yanghu.luo
 * @create: 2023-05-26 18:06
 * @Description: 股票逐笔成交明细
 */
@Data
public class StockRecordDetailsVO extends BaseRequest {


	/**
	 * 资产ID
	 */
	private String assetId;

	private String stockCode;

	/**
	 * 委托编号
	 */
	private String orderNo;

	// 市场
	private String exchangeType;

	private String startDate;

	private String endDate;
}
