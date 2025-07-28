package com.minigod.zero.manage.vo;

import lombok.Data;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/8/16 17:01
 * @description：
 */
@Data
public class SmsMessageRecord {
	private Long id;
	private String cellPhone;
	private String content;
	private String channelName;
	private String sendStatusName;
	private String sendTime;
	private String discription;
	private String errorMessage;
}
