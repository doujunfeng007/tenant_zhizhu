package com.minigod.zero.trade.afe.req;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

/**
 * @author chen
 * @ClassName StockMovementQueryRequest.java
 * @Description 股票流水查询
 * @createTime 2024年04月26日 18:55:00
 */
@Data
public class StockMovementQueryRequest extends CommonRequest{

	@JSONField(name ="CLIENT_ID")
	private String clientId;

	@JSONField(name ="FROM")
	private String from;

	@JSONField(name ="TO")
	private String to;
}
