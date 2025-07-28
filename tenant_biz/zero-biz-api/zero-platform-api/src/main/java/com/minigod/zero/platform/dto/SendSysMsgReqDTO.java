package com.minigod.zero.platform.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SendSysMsgReqDTO implements Serializable {
	private static final long serialVersionUID = -2260388125919493487L;

	@ApiModelProperty("主键")
	private Long id;

	private Integer tempCode;//通知模板编码

	@ApiModelProperty("标题")
	private String title;

	@ApiModelProperty("通知内容")
	private String content;

	@ApiModelProperty("客户端类型(0-全部终端 1-Android 2-IOS)")
	private Integer clientType;

	@ApiModelProperty("消息分组(P-个人，A-全站，L-标签用户，T-用户分组)")
	private String msgGroup;

	@ApiModelProperty("推送类型(0-强消息 1-弱消息)")
	private Integer sendType;

	@ApiModelProperty("推送方式(0-即时 1-定时)")
	private Integer sendWay;

	@ApiModelProperty("推送时间,推送方式为定时时,不为空")
	private Date sendTime;

	@ApiModelProperty("推送状态(0-未发送，1-已发送 2-发送失败)")
	private String sendStatus = "0";

	@ApiModelProperty("重发次数")
	private Integer retryCnt = 0;

	@ApiModelProperty("是否弹窗提示 1-是 0-否")
	private Integer popFlag = 0;

	@ApiModelProperty("弹窗失效时间 例2023-07-01")
	private String popInvalidTime;

	private Long busId;

	private Long[] userIds;

	private String url;

}
