package com.minigod.zero.trade.afe.req;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author chen
 * @ClassName HoldingQueryRequest.java
 * @Description TODO
 * @createTime 2024年04月24日 11:50:00
 */
@Data
public class HoldingQueryRequest extends CommonRequest{

	@JSONField(name ="CLIENT_ID")
	private String clientId;
}
