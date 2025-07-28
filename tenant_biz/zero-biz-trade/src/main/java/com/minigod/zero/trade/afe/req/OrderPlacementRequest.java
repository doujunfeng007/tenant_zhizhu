package com.minigod.zero.trade.afe.req;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author chen
 * @ClassName OrderPlacementRequest.java
 * @Description 下单
 * @createTime 2024年04月17日 19:49:00
 */
@Data
public class OrderPlacementRequest extends CommonRequest{

	@JSONField(name ="CLIENT_ID")
	private String clientId;


	/**
	 * For Northbound order, MARKET=”CN”, For
	 * US market, MARKET=“US”, For SG Market,
	 * MARKET=”SG    香港HK
	 */
	@JSONField(name ="MARKET")
	private String market;

	/**
	 * For SG, US and CN market, only “LO” order
	 * type accepted. For PhillipMart pre-market
	 * order, only accept “PM”
	 * 对于SG、美国和CN市场，只接受“LO”订单类型。对于菲律宾的上市前订单，只接受“PM”
	 */
	@JSONField(name ="MATCHING_TYPE")
	private String matchingType;

	/**
	 * Buy/Sell
	 */
	@JSONField(name ="BID_ASK_TYPE")
	private String bidAskType;

	/**
	 *
	 */
	@JSONField(name ="INSTRUMENT_NO")
	private String instrumentNo;

	/**
	 * 价格
	 */
	@JSONField(name ="PRICE")
	private String price;

	/**
	 * 数量
	 */
	@JSONField(name ="QUANTITY")
	private String quantity;

	/**
	 * 最高价格 非必填
	 */
	@JSONField(name ="HIGHER_PRICE")
	private String higherPrice;
	/**
	 * 最低价格 非必填
	 */
	@JSONField(name ="LOWER_PRICE")
	private String lowerPrice;

	/**
	 * The date format is “YYYY-MM-DD” and
	 * equals to the current trading date.
	 * 非必填
	 */
	@JSONField(name ="GOOD_TILL_DATE")
	private String goodTillDate ;

	/**
	 * 订单id 非必填
	 */
	@JSONField(name ="CLORDID")
	private String clorderId;

	/**
	 * ip 非必填 如果为空则默认为客户端IP
	 */
	@JSONField(name ="REMOTE_IP")
	private String remoteIp;

	@JSONField(name ="CLIENT_BCAN")
	private String clientBCan;



}
