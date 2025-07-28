package com.minigod.zero.log.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="API-Log查询参数")
public class LogApiDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "日志类型")
	private String type;
	@ApiModelProperty(value = "日志标题")
	private String title;
	@ApiModelProperty(value = "日志时间")
	private String time;
	@ApiModelProperty(value = "日志ID")
	protected Long id;
	@ApiModelProperty(value = "租户ID")
	private String tenantId;
	@ApiModelProperty(value = "服务ID")
	protected String serviceId;
	@ApiModelProperty(value = "服务IP")
	protected String serverIp;
	@ApiModelProperty(value = "服务主机")
	protected String serverHost;
	@ApiModelProperty(value = "环境")
	protected String env;
	@ApiModelProperty(value = "请求地址")
	protected String remoteIp;
	@ApiModelProperty(value = "浏览器")
	protected String userAgent;
	@ApiModelProperty(value = "请求URI")
	protected String requestUri;
	@ApiModelProperty(value = "请求方式")
	protected String method;
	@ApiModelProperty(value = "请求类")
	protected String methodClass;
	@ApiModelProperty(value = "请求方法")
	protected String methodName;
	@ApiModelProperty(value = "开始时间 yyyy-mm-dd", example = "2021-12-01", notes = "日志查询起始时间")
	private String startTime;
	@ApiModelProperty(value = "结束时间 yyyy-mm-dd", example = "2021-12-31", notes = "日志查询结束时间")
	private String endTime;
}
