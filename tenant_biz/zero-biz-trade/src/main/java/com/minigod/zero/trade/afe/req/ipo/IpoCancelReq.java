package com.minigod.zero.trade.afe.req.ipo;

import com.alibaba.fastjson.annotation.JSONField;
import com.minigod.zero.trade.afe.req.CommonRequest;
import lombok.Data;

/**
 * @ClassName: com.minigod.zero.trade.afe.req.ipo.IpoCancelReq
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/4/22 18:20
 * @Version: 1.0
 */
@Data
public class IpoCancelReq extends CommonRequest {
	@JSONField(name = "ORDER_NO")
	private String orderNo;

	@JSONField(name ="CLIENT_ID")
	private String clientId;


}
