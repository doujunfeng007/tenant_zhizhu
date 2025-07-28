package com.minigod.zero.log.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


@Data
@ApiModel(description = "ErrorLog日志")
public class LogErrorVO implements Serializable {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "日志ID")
	@ExcelProperty("日志ID")
	protected Long id;
	@ApiModelProperty(value = "日志堆栈")
	@ExcelProperty("日志堆栈")
	private String stackTrace;
	@ApiModelProperty(value = "异常名称")
	@ExcelProperty("异常名称")
	private String exceptionName;
	@ApiModelProperty(value = "异常信息")
	@ExcelProperty("异常信息")
	private String message;
	@ApiModelProperty(value = "异常文件名称")
	@ExcelProperty("异常文件名称")
	private String fileName;
	@ApiModelProperty(value = "异常行号")
	@ExcelProperty("异常行号")
	private Integer lineNumber;
	@ApiModelProperty(value = "服务ID")
	@ExcelProperty("服务ID")
	protected String serviceId;
	@ApiModelProperty(value = "服务IP")
	@ExcelProperty("服务IP")
	protected String serverIp;
	@ApiModelProperty(value = "服务主机名")
	@ExcelProperty("服务主机名")
	protected String serverHost;
	@ApiModelProperty(value = "系统环境")
	@ExcelProperty("系统环境")
	protected String env;
	@ApiModelProperty(value = "请求地址")
	@ExcelProperty("请求地址")
	protected String remoteIp;
	@ApiModelProperty(value = "用户代理")
	@ExcelProperty("用户代理")
	protected String userAgent;
	@ApiModelProperty(value = "请求URI")
	@ExcelProperty("请求URI")
	protected String requestUri;
	@ApiModelProperty(value = "请求方式")
	@ExcelProperty("请求方式")
	protected String method;
	@ApiModelProperty(value = "请求类")
	@ExcelProperty("请求类")
	protected String methodClass;
	@ApiModelProperty(value = "请求方法")
	@ExcelProperty("请求方法")
	protected String methodName;
	@ApiModelProperty(value = "请求参数")
	@ExcelProperty("请求参数")
	protected String params;
	@ApiModelProperty(value = "创建者")
	@ExcelProperty("创建者")
	protected String createBy;
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@ApiModelProperty(value = "创建时间")
	@ExcelProperty("创建时间")
	protected Date createTime;
}
