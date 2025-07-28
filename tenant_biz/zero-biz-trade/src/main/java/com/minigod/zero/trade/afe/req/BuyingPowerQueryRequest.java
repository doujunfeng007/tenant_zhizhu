package com.minigod.zero.trade.afe.req;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

/**
 * @author chen
 * @ClassName BuyingPowerQueryRequest.java
 * @Description 资金汇总查询
 * @createTime 2024年04月24日 17:30:00
 */
@Data
public class BuyingPowerQueryRequest extends CommonRequest{

	@JSONField(name ="CLIENT_ID")
	private String clientId;

	@JSONField(name ="INSTRUMENT_CODE")
	private String instrumentCode;

	@JSONField(name ="MARKET")
	private String market;
}
