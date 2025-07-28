package com.minigod.zero.trade.afe.resp;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * @author chen
 * @ClassName OrderBookHistoryResponse.java
 * @Description TODO
 * @createTime 2024年04月18日 17:06:00
 */
@Data
public class OrderBookHistoryResponse {

	@JSONField(name = "ORDER_BOOK")
	private List<OrderBook> orderBook ;

	@Data
	public static class OrderBook {

		@JSONField(name ="MARKET")
		private String market;

		@JSONField(name ="ORDER_NO")
		private String orderNo;

		@JSONField(name ="MATCHING_TYPE")
		private String matchingType;

		@JSONField(name ="BID_ASK_TYPE")
		private String bidAskType;

		@JSONField(name ="INSTRUMENT_NO")
		private String instrumentNo;

		@JSONField(name ="PRICE")
		private String price;

		@JSONField(name ="QUANTITY")
		private String quantity;

		@JSONField(name ="EXECUTED_QUANTITY")
		private String executedQuantity;

		@JSONField(name ="OUTSTANDING_QUANTITY")
		private String outstandingQuantity;

		@JSONField(name ="ORDER_STATUS")
		private String orderStatus;

		@JSONField(name ="HIGHER_PRICE")
		private String higherPrice;

		@JSONField(name ="LOWER_PRICE")
		private String lowerPrice;

		@JSONField(name ="GOOD_TILL_DATE")
		private String goodTillDate;

		@JSONField(name ="CHANNEL")
		private String channel;

		@JSONField(name ="ORDER_SUBMIT_DATETIME")
		private String orderSubmitDatetime;

		@JSONField(name ="DESTINATION")
		private String destination;

		@JSONField(name ="ORDER_ID")
		private String orderId;

		@JSONField(name ="EXECUTION")
		private List<Execution> execution;


	}
	@Data
	public static class Execution {

		@JSONField(name ="PRICE")
		private String price;

		@JSONField(name ="EXECUTED_QUANTITY")
		private String executedQuantity;

		@JSONField(name ="EXECUTION_DATETIME")
		private String executionDatetime;

		@JSONField(name ="MATCHING_NO")
		private String matchingNo;
	}
}
