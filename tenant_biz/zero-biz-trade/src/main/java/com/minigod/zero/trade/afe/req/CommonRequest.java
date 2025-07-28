package com.minigod.zero.trade.afe.req;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * @author chen
 * @ClassName CommonRequest.java
 * @Description TODO
 * @createTime 2024年04月17日 10:41:00
 */
@Data
public class CommonRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 接口类型命令
	 */
	@JSONField(name ="Command")
	private String command;

	/**
	 * lid
	 */
	@JSONField(name = "Lid")
	private String lid;

	/**
	 * The value can be “EN”,
	 * “CN” or “TW” for
	 * “English”, “Simplified
	 * Chinese” or “Traditional
	 * Chinese” character sets
	 * respectively
	 */
	@JSONField(name ="LANG")
	private String lang;

	/**
	 * 渠道 The value can be “MB”,
	 * “XA” or “XW”. If null, will
	 * default “MB”.)
	 */
	@JSONField(name = "CHANNEL")
	private String channel;

	@JSONField(name ="SESSION_KEY")
	private String sessionKey;


	@JSONField(name ="REQ_ID")
	private String reqId;
}
