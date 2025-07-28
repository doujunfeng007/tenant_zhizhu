package com.minigod.zero.trade.afe.req;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author chen
 * @ClassName OrderAmendmentRequest.java
 * @Description 改单请求参数
 * @createTime 2024年04月18日 13:56:00
 */
@Data
public class OrderAmendmentRequest extends CommonRequest{

	@JSONField(name ="CLIENT_ID")
	private String clientId;

	@JSONField(name ="ORDER_NO")
	private String orderNo;

	@JSONField(name ="QUANTITY")
	private String quantity;

	/**
	 * The value can be a floating-point
	 * number or null for “AO” order
	 */
	@JSONField(name ="PRICE")
	private String price;

}
