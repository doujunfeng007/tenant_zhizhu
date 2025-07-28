package com.minigod.zero.trade.afe.req;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author chen
 * @ClassName OrderCancellationRequest.java
 * @Description 撤单请求参数
 * @createTime 2024年04月18日 14:07:00
 */
@Data
public class  OrderCancellationRequest extends CommonRequest{

	/**
	 * 订单号
	 */
	@JSONField(name ="ORDER_NO")
	private String orderNo;

	@JSONField(name ="CLIENT_ID")
	private String clientId;


}
