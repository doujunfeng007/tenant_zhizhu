package com.minigod.zero.trade.afe.req;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author chen
 * @ClassName OrderBookHistoryRequest.java
 * @Description 历史订单查询
 * @createTime 2024年04月18日 14:20:00
 */
@Data
public class OrderBookHistoryRequest extends CommonRequest{

	/**
	 * 客户ID
	 */
	@JSONField(name ="CLIENT_ID")
	private String clientId;

	@JSONField(name ="FROM")
	private String from;

	@JSONField(name ="TO")
	private String to;


}
