package com.minigod.zero.trade.afe.req.ipo;

import com.alibaba.fastjson.annotation.JSONField;
import com.minigod.zero.trade.afe.req.CommonRequest;
import lombok.Data;

/**
 * @ClassName: com.minigod.zero.trade.afe.req.ipo.OrderBookQueryReq
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/4/19 16:37
 * @Version: 1.0
 */
@Data
public class OrderBookQueryReq extends CommonRequest {
	/**
	 * 客户ID
	 */
	@JSONField(name ="CLIENT_ID")
	private String clientId;
}
