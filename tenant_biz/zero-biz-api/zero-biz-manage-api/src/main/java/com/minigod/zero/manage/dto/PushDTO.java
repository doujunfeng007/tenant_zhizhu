package com.minigod.zero.manage.dto;

import lombok.Data;

import java.util.Date;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/8/16 15:54
 * @description：
 */
@Data
public class PushDTO {
	/**
	 * {@link com.minigod.zero.platform.enums.MsgStaticType.DisplayGroup}
	 */
	private Integer displayGroup;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 内容
	 */
	private String content;
	/**
	 *客户端类型 0-全部终端 1-Android 2-IOS
	 */
	private Integer clientType;
	/**
	 * 消息分组(P-个人，A-全站，L-标签用户，T-用户分组)
	 */
	private String msgGroup;

	private Integer targetId;
	/**
	 * 推送类型(0-强消息 1-弱消息)
	 */
	private Integer sendType;
	/**
	 * {@link com.minigod.zero.platform.enums.InformEnum.SendWayTimeEnum}
	 */
	private Integer sendWay;
	/**
	 * 定时消息发送时间
	 */
	private Date sendTime;
	/**
	 * 接收用户，多个逗号分隔
	 */
	private String custId;
	/**
	 * 跳转地址 多个逗号分隔
	 */
	private String url;
}
