package com.minigod.zero.trade.afe.req;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author chen
 * @ClassName OrderSummaryQueryRequest.java
 * @Description TODO
 * @createTime 2024年04月24日 19:24:00
 */
@Data
public class OrderSummaryQueryRequest extends CommonRequest{

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
