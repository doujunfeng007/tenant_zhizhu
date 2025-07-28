package com.minigod.zero.trade.afe.resp;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author chen
 * @ClassName OrderPlacementResponse.java
 * @Description TODO
 * @createTime 2024年04月17日 20:21:00
 */
@Data
public class OrderPlacementResponse {

	@JSONField(name ="LOGIN_ID")
	private String loginId;

	@JSONField(name ="CLIENT_ID")
	private String clientId;

	@JSONField(name ="CHANNEL")
	private String channel;

	@JSONField(name ="SESSION_KEY")
	private String sessionKey;

	@JSONField(name ="REQ_TXN_ID")
	private String reqTxnId;

	@JSONField(name ="ORDER_NO")
	private String orderNo;

	@JSONField(name ="MARKET")
	private String market;

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

	@JSONField(name ="HIGHER_PRICE")
	private String higherPrice;

	@JSONField(name ="LOWER_PRICE")
	private String lowerPrice;

	@JSONField(name ="GOOD_TILL_DATE")
	private String goodTillDate;

	@JSONField(name ="DESTINATION")
	private String destination;

	@JSONField(name ="REMOTE_IP")
	private String remoteIp;

	@JSONField(name ="CLIENT_BCAN")
	private String clientBCan;

	@JSONField(name ="CLORDID")
	private String clorderId;

	@JSONField(name ="STATUS")
	private String status;

	@JSONField(name ="Msg_ID")
	private String msgId;



}
