package com.minigod.zero.platform.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysMsgSearchReqDTO implements Serializable {
	private static final long serialVersionUID = -2260388125919493487L;

	@ApiModelProperty("主键")
	private Long id;

	@ApiModelProperty("用户id")
	private Long targetId;

	@ApiModelProperty("标题")
	private String title;

	@ApiModelProperty("消息分组(P-个人，A-全站，L-标签用户，T-用户分组)")
	private String msgGroup;

	@ApiModelProperty("客户端类型(0-全部终端 1-Android 2-IOS)")
	private Integer clientType;

	@ApiModelProperty("推送状态(0-未发送，1-已发送 2-发送失败)")
	private Integer sendStatus;

	@ApiModelProperty("开始时间")
	private String sendTimeStart;

	@ApiModelProperty("结束时间")
	private String sendTimeEnd;

	private Date startDate;
	private Date endDate;
}
