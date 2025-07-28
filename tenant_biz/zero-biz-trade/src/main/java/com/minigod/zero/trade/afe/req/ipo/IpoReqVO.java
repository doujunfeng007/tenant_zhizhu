package com.minigod.zero.trade.afe.req.ipo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @ClassName: com.minigod.zero.trade.afe.req.ipo.IpoVO
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/4/19 16:33
 * @Version: 1.0
 */
@Data
public class IpoReqVO {
	@JSONField(name = "Command")
	private String command;
	@JSONField(name = "Lid")
	private String lid;
	@JSONField(name = "CHANNEL")
	private String channel;
	@JSONField(name = "SESSION_KEY")
	private String sessionKey;
	@JSONField(name = "REQ_ID")
	private String reqId;
	@JSONField(name = "CLIENT_ID")
	private String clientId;
	@JSONField(name = "LANG")
	private String lang;


}
