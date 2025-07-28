package com.minigod.zero.platform.core;

import lombok.Data;

import java.util.List;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/12/10 16:31
 * @description：
 */
@Data
public class Message {
	/**
	 * 消息id
	 */
	private String msgId;
	/**
	 * 模板
	 */
	private Integer templateCode;
	/**
	 * 租户
	 */
	private String tenantId;
	/**
	 * 语种
	 */
	private String language;
	/**
	 * 参数
	 */
	private List<String> templateParam;

	/**
	 * 消息内容
	 */
	private String content;
	/**
	 * 消息标题
	 */
	private String title;
	/**
	 * 消息标题参数
	 */
	private List<String> titleParam;

}
