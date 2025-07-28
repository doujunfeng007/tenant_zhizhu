package com.minigod.zero.platform.dto;

import com.minigod.zero.platform.common.NotifyMsg;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class SaveMessageReqDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Long> lstToUserId;
	private Long fromUserId;
	private int displayGroup;
	private String title;
	private String msgContent;
	private NotifyMsg notifyMsg;

	private String sendId; // 唯一ID，外部系统调用发送消息传入
	private Integer sendWay;//推送方式(0-即时 1-定时)
	private Date sendTime;//推送时间,推送方式为定时时,不为空
	private Integer sendStatus ;//推送状态(0-未发送，1-已发送 2-发送失败)
	private String msgGroup ;//消息分组(P-个人，A-全站，L-标签用户，T-用户分组)
	private Integer clientType;//客户端类型(0-全部终端 1-Android 2-IOS)
	private String url;
	private String tenantId;

	/**
	 * 相同KEY reSentPreventSeconds 秒内不再重发相同请求
	 */
	private boolean autoPreventResent = false;;
	private int reSentPreventSeconds = 60 * 60 *24;

}
