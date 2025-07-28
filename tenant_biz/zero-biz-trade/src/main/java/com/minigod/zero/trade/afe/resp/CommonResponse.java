package com.minigod.zero.trade.afe.resp;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * @author chen
 * @ClassName CommonResponse.java
 * @Description TODO
 * @createTime 2024年04月17日 10:51:00
 */
@Data
public class CommonResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 消息ID
	 */
	@JSONField(name ="Msg_ID")
	private String msgId;

	/**
	 * 登录ID
	 */
	@JSONField(name ="LOGIN_ID")
	private String loginId;

	/**
	 * 客户ID
	 */
	@JSONField(name ="CLIENT_ID")
	private String clientId;

	/**
	 * 渠道 The value can be “MB”,
	 * “XA” or “XW”. If null,
	 * will default “MB”.
	 */
	@JSONField(name ="CHANNEL")
	private String channel;

	/**
	 * 登录会话KEY
	 */
	@JSONField(name ="SESSION_KEY")
	private String sessionKey;

	/**
	 * The value can be “EN”,
	 * “CN” or “TW” for
	 * “English”, “Simplified
	 * Chinese” or
	 * “Traditional Chinese”
	 * character sets
	 * respectively
	 */
	@JSONField(name ="STATUS")
	private String status;

	/**
	 * 错误消息
	 */
	@JSONField(name ="ERROR_MESSAGE")
	private String errorMessage;

	/**
	 * 错误code
	 */
	@JSONField(name ="ERROR_CODE")
	private String errorCode;

	@JSONField(name ="RESULT")
	private Object result;

	@JSONField(name ="REQ_ID")
	private String reqId;
}
