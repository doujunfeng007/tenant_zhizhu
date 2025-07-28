package com.minigod.zero.cust.apivo.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 设备信息
 */
@Data
@ApiModel(value = "客户设备信息", description = "客户设备信息")
public class DeviceInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 设备ID由服务器生成
	 */
	/*@ApiModelProperty(value = "设备ID")
	private Integer deviceId;*/

	/**
	 * 设备ID(目前仅用于iOS设备)
	 */
	@ApiModelProperty(value = "设备Code", required = true)
	private String deviceCode;


	/**
	 * 设备名称(用户自定义设备名称，从客户端上传，可以在网站修改，用于客户识别不同的设备)
	 */
	@ApiModelProperty(value = "设备名称(用户自定义设备名称，从客户端上传，可以在网站修改，用于客户识别不同的设备)",required = true)
	private String deviceName;

	/**
	 * 设备型号(如：“iPhone 5S”)
	 */
	@ApiModelProperty(value = "设备型号(如：“iPhone 5S”)", required = true)
	private String deviceModel;

	/**
	 * 设备类型(0PC;1手机;2平板)
	 */
	@ApiModelProperty(value = "设备类型(0PC;1手机;2平板)", required = true)
	private Integer deviceType;
	/**
	 * 操作系统版本
	 */
	@ApiModelProperty(value = "操作系统版本(如:REL)", required = true)
	private String osVersion;

	/**
	 * 操作系统类型(0安卓，1苹果，2WP)
	 */
	@ApiModelProperty(value = "0安卓，1苹果，2WP", required = true)
	private Integer osType;

	/**
	 * App应用版本
	 */
	@ApiModelProperty(value = "App应用版本号", required = true)
	private String appVersion;

	/**
	 * 第三方open_code
	 */
	@ApiModelProperty(value = "第三方open_code(极光ID)", required = true)
	private String openCode;

	/**
	 * 渠道标识，应用是从哪个
	 */
	@ApiModelProperty(value = "渠道标识", hidden = true)
	private String channel;

	private Integer customType;
}
