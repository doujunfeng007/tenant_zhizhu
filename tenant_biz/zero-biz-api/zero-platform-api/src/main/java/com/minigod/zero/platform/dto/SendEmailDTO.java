package com.minigod.zero.platform.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SendEmailDTO {

	@ApiModelProperty("标题")
	private String title;

	@ApiModelProperty("内容")
	private String content;

	@ApiModelProperty("模板编码")
	private int code;

	@ApiModelProperty("模板参数")
	private List<String> list = new ArrayList<>();

	@ApiModelProperty("发送人")
	private String sender;

	@ApiModelProperty("发送人名称")
	private String fromName;

	@ApiModelProperty("附件url")
	private List<String> attachmentUrls;

	@ApiModelProperty("抄送人")
	private List<String> carbonCopy;

	@ApiModelProperty("密抄送人")
	private List<String> blindCarbonCopy;

	@ApiModelProperty("唯一ID，外部系统调用发送消息传入")
	private String sendId;

	@ApiModelProperty("接收人")
	private List<String> accepts;

	@ApiModelProperty("简体:zh-hans 繁体:zh-hant 英文:en")
	private String lang;

	/**
	 * 邮件验证码key
	 */
	private String captchaKey;
	/**
	 * 验证码
	 */
	private String captchaCode;
	/**
	 * 租户id
	 */
	private String tenantId;
	/**
	 * 动态标题参数
	 */
	private List<String> titleParam;


}
