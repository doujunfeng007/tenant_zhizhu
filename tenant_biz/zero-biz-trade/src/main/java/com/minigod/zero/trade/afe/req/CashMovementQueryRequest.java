package com.minigod.zero.trade.afe.req;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author chen
 * @ClassName CashMovementQueryRequest.java
 * @Description 资金流水
 * @createTime 2024年04月26日 17:53:00
 */
@Data
public class CashMovementQueryRequest extends CommonRequest{

	@JSONField(name ="CLIENT_ID")
	private String clientId;

	@JSONField(name ="FROM")
	private String from;

	@JSONField(name ="TO")
	private String to;
}
