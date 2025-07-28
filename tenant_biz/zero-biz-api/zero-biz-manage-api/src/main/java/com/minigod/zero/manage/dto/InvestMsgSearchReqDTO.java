package com.minigod.zero.manage.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class InvestMsgSearchReqDTO implements Serializable {
	private static final long serialVersionUID = -2260388125919493487L;

	@ApiModelProperty("通知模板编码")
	private Integer userId;

	@ApiModelProperty("推送开始时间")
	private String sendTimeStart;

	@ApiModelProperty("推送结束时间")
	private String sendTimeEnd;

	@ApiModelProperty("标题")
	private String title;

	@ApiModelProperty("id")
	private String id;

	@ApiModelProperty("目标用户,P-个人，A-全站")
	private String msgGroup;
	@ApiModelProperty("目标平台,0-安卓 1-IOS系统 2-WP系统 3-全部")
	private String clientType;

}
