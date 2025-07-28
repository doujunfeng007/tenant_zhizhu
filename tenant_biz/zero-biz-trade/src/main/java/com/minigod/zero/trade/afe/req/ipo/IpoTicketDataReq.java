package com.minigod.zero.trade.afe.req.ipo;

import com.alibaba.fastjson.annotation.JSONField;
import com.minigod.zero.trade.afe.req.CommonRequest;
import lombok.Data;

/**
 * @ClassName: com.minigod.zero.trade.afe.req.ipo.IpoTicketDataReq
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/4/25 19:05
 * @Version: 1.0
 */
@Data
public class IpoTicketDataReq extends CommonRequest {
	/**
	 * 客户ID
	 */
	@JSONField(name ="CLIENT_ID")
	private String clientId;
}
