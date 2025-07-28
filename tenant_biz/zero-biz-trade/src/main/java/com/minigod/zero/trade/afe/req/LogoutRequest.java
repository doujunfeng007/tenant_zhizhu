package com.minigod.zero.trade.afe.req;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author chen
 * @ClassName LogoutRequest.java
 * @Description TODO
 * @createTime 2024年12月27日 18:34:00
 */
@Data
public class LogoutRequest extends CommonRequest{

	@JSONField(name ="CLIENT_ID")
	private String clientId;
}
