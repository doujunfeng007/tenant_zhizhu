/**
 * @Title: SysMsgReqVO.java
 * @Copyright: © 2015 sunline
 * @Company: sunline
 */

package com.minigod.zero.platform.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author sunline
 * @version v1.0
 * @description
 * @date 2015-6-3 下午5:28:33
 */
@Data
public class PlatformSysMsgReqDTO implements Serializable {
	private static final long serialVersionUID = -2260388125919493487L;
	private Long id;//主键
	private String msgType;//消息类型(A:活动 R:提醒 N:公告 X:要闻 B:播报)
	private String msgLevel;//消息级别(I:重要 G:普通)
	private Integer tempCode;//通知模板编码
	private String title;//标题
	private String content;//通知内容
	private Integer clientType;//客户端类型(0-全部终端 1-Android 2-IOS)
	private String msgGroup;//消息分组(P-个人，A-全站，L-标签用户，T-用户分组)
	private Integer sendType;//推送类型(0-强消息 1-弱消息)
	private Integer sendWay;//推送方式(0-即时 1-定时)
	private Date sendTime;//推送时间,推送方式为定时时,不为空
	private String sendStatus = "0";//推送状态(0-未发送，1-已发送 2-发送失败)
	private Integer retryCnt = 0;//重发次数

	String confirmName;
	String password;
	private Long busId;

	Long[] userIds;

}
