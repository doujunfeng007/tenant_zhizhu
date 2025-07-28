package com.minigod.zero.trade.afe.resp;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * @author chen
 * @ClassName OrderSummaryQueryResponse.java
 * @Description TODO
 * @createTime 2024年04月24日 19:29:00
 */
@Data
public class OrderSummaryQueryResponse {

	@JSONField(name = "ORDER_SUMMARY")
	private List<OrderSummary> orderSummaries;

	@Data
	public static  class OrderSummary {
		@JSONField(name = "MARKET")
		private String market;

		@JSONField(name = "ORDER_NO")
		private String orderNo;

		@JSONField(name = "MATCHING_TYPE")
		private String matchingType;

		@JSONField(name = "BID_ASK_TYPE")
		private String bidAskType;

		@JSONField(name = "INSTRUMENT_NO")
		private String instrumentNo;

		@JSONField(name = "PRICE")
		private String price;

		@JSONField(name = "QUANTITY")
		private String quantity;

		@JSONField(name = "EXECUTED_QUANTITY")
		private String executedQuantity;

		@JSONField(name = "ORDER_STATUS")
		private String orderStatus;

		@JSONField(name = "HIGHER_PRICE")
		private String higherPrice;

		@JSONField(name = "LOWER_PRICE")
		private String lowerPrice;

		@JSONField(name = "GOOD_TILL_DATE")
		private String goodTillDate;

		@JSONField(name = "CHANNEL")
		private String channel;

		@JSONField(name = "ORDER_SUBMIT_DATETIME")
		private String orderSubmitDatetime;

		@JSONField(name = "ORDER_ID")
		private String orderId;


	}
}
