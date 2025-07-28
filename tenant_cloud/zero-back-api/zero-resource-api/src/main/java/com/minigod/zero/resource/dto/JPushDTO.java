package com.minigod.zero.resource.dto;

import lombok.Data;

import java.util.List;

/**
 * 极光推送DTO
 *
 * @author Zhe.Xiao
 */
@Data
public class JPushDTO {
	/**
	 * 极光应用鉴权
	 */
	private String authorization;
	/**
	 * 极光设备注册号
	 */
	private List<String> lstTokenId;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 消息体
	 */
	private String transMessage;
	/**
	 * 系统类型
	 */
	private int osType;
	/**
	 * 推送类型
	 */
	private int pushType;
	/**
	 * 是否线下推送
	 */
	private Boolean offlinePush;
	/**
	 * IOS apns测试环境标识
	 */
	private Boolean apnsTest;
}
