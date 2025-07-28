package com.minigod.zero.platform.dto;

import com.minigod.zero.platform.enums.PushTypeEnum;
import com.minigod.zero.platform.vo.CustomerDeviceInfoVO;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Zhe.Xiao
 * <p>
 * 消息推送feign接口入参
 */
@Data
public class SendNotifyDTO {
	/**
	 * 接收用户列表
	 */
	private List<Long> lstToUserId;

	/**
	 * 消息显示分组 【12015-交易提醒  13001-行情提醒  13002-新股提醒  12007-服务通知  13003-活动推送  13004-热点资讯】
	 */
	private int displayGroup;

	/**
	 * 通知页面消息标题，如果不传则用模板标题
	 */
	private String title;

	/**
	 * 通用模版消息，如果有传templateCode，则使用模板内容
	 */
	private String msgContent;

	/**
	 * 推送方式(0-即时 1-定时)
	 */
	private Integer sendWay;

	/**
	 * 推送时间,推送方式为定时时,不为空
	 */
	private Date sendTime;

	/**
	 * 推送时间 (格式) ： yyyy-MM-dd HH:mm:ss
	 */
	private String sendTimeStr;

	/**
	 * 消息分组(P-个人，A-全站，L-标签用户，T-用户分组)
	 */
	private String msgGroup;

	/**
	 * 推送至客户端类型(0-全部终端 1-Android 2-IOS)，传1只推给安卓设备，传2只推给IOS设备，不传则默认全部终端
	 */
	private Integer clientType;

	/**
	 * 模板代码，对应platform_common_template表的temp_code
	 */
	private Integer templateCode;

	/**
	 * 带参数的消息模板中的参数列表
	 */
	private List<String> params = new ArrayList<>();

	/**
	 * 发送人ID
	 */
	private Long fromUserId;

	/**
	 * 简体:zh-hans 繁体:zh-hant 英文:en
	 */
	private String lang;
	/**
	 * 跳转链接（使用与前端定义的跳转协议，参考PushUrlProtocol）
	 * 如果模板有配置url，且此入参有值则覆盖模板的url
	 */
	private String url;
	/**
	 * 跳转链接参数列表
	 */
	private List<String> urlParams = new ArrayList<>();
	/**
	 * 设备信息
	 */
	private List<CustomerDeviceInfoVO> deviceInfoList;

	/**
	 * 推送类型 {@link PushTypeEnum}
	 */
	private Integer pushType;
	/**
	 * 租户
	 */
	private String tenantId;

}
