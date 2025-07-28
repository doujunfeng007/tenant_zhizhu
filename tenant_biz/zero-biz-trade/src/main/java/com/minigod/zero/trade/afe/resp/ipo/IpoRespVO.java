package com.minigod.zero.trade.afe.resp.ipo;

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
public class IpoRespVO<T> {
	/**
	 * 登录会话的登录ID
	 */
	@JSONField(name = "LOGIN_ID")
	private String loginId;

	@JSONField(name = "CLIENT_ID")
	private String clientId;

	@JSONField(name = "CHANNEL")
	private String channel;

	@JSONField(name = "SESSION_KEY")
	private String sessionKey;

	@JSONField(name = "REQ_ID")
	private String reqId;

	@JSONField(name = "STATUS")
	private String status;

	@JSONField(name = "RESULT")
	private IpoResultVO<T> result;

	@JSONField(name = "Msg_ID")
	private String msgId;
}
