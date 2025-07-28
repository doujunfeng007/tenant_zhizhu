package com.minigod.zero.log.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="UsualLog日志查询参数")
public class LogUsualDTO implements Serializable {
	@ApiModelProperty(value = "日志ID")
	protected Long id;
	@ApiModelProperty(value = "日志级别")
	private String logLevel;
	@ApiModelProperty(value = "日志ID")
	private String logId;
	@ApiModelProperty(value = "日志内容")
	private String logData;
	@ApiModelProperty(value = "服务ID")
	protected String serviceId;
	@ApiModelProperty(value = "服务IP")
	protected String serverIp;
	@ApiModelProperty(value = "服务主机名")
	protected String serverHost;
	@ApiModelProperty(value = "系统环境")
	protected String env;
	@ApiModelProperty(value = "请求地址")
	protected String remoteIp;
	@ApiModelProperty(value = "用户代理")
	protected String userAgent;
	@ApiModelProperty(value = "请求URI")
	protected String requestUri;
	@ApiModelProperty(value = "请求方式")
	protected String method;
	@ApiModelProperty(value = "请求类")
	protected String methodClass;
	@ApiModelProperty(value = "请求方法")
	protected String methodName;
	@ApiModelProperty(value = "请求参数")
	protected String params;
	@ApiModelProperty(value = "创建者")
	protected String createBy;
	@ApiModelProperty(value = "开始时间 yyyy-mm-dd", example = "2021-12-01 12:15:05", notes = "日志查询起始时间")
	private String startTime;
	@ApiModelProperty(value = "结束时间 yyyy-mm-dd", example = "2021-12-31 23:59:59", notes = "日志查询结束时间")
	private String endTime;
}
