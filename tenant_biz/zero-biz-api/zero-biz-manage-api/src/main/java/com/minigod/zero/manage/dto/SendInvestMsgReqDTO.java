package com.minigod.zero.manage.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class SendInvestMsgReqDTO {
	private static final long serialVersionUID = -2260388125919493487L;

	private Long id;

	@ApiModelProperty("消息类别：【12015-交易提醒 13001-行情提醒 13002-新股提醒 12007-服务通知 13003-活动推送 13004-热点资讯】")
	private Integer displayGroup = 12007;

	@ApiModelProperty("通知模板编码")
	private Integer tempCode;

	@ApiModelProperty("标题")
	private String title;

	@ApiModelProperty("通知内容")
	private String content;

	@ApiModelProperty("客户端类型(0-全部终端 1-Android 2-IOS)")
	private Integer clientType;

	@ApiModelProperty("消息分组(P-个人，A-全站，L-标签用户，T-用户分组)")
	private String msgGroup;

	@ApiModelProperty("msg_group=P时,记录为用户id;当msg_group=A时,为0")
	private Integer targetId;

	@ApiModelProperty("推送类型(0-强消息 1-弱消息)")
	private Integer sendType;

	@ApiModelProperty("推送方式(0-即时 1-定时)")
	private Integer sendWay;

	@ApiModelProperty("推送时间,推送方式为定时时,不为空")
	private Date sendTime;

	private Long busId;

	@ApiModelProperty("用户id")
	private Long[] userIds;

	@ApiModelProperty("触达用户类型：0=所有用户，1=特定用户，2=渠道白名单，3=渠道黑名单")
	private String showType;

	@ApiModelProperty("渠道数组")
	private String[] channelIds;

	@ApiModelProperty("跳转地址")
	private String url;

//	private Integer isSync; //是否同步站内信 0否 1是

}
