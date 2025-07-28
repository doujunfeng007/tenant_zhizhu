package com.minigod.zero.cust.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 登陆日志表 实体类
 *
 * @author 掌上智珠
 * @since 2023-03-27
 */
@Data
@TableName("cust_login_log")
@ApiModel(value = "CustLoginLog对象", description = "登陆日志表")
public class CustLoginLogEntity implements Serializable {

	@ApiModelProperty(value = "主键")
	private Long id;

	/**
	 * 用户ID
	 */
	@ApiModelProperty(value = "用户ID")
	private Long custId;
	/**
	 * 1：登入，0：登出，-1：异常
	 */
	@ApiModelProperty(value = "1：登入，0：登出，-1：异常")
	private Integer action;
	/**
	 * 类型 1-客户登陆，2-交易解锁 3-异地访问
	 */
	@ApiModelProperty(value = "类型 1-客户登陆，2-交易解锁，3-异地访问")
	private Integer type;
	/**
	 * 登录方式：1-手机号，2-邮箱，3-用户号，4-交易账号，5-第三方
	 */
	@ApiModelProperty(value = "登录方式：1-手机号，2-邮箱，3-用户号，4-交易账号，5-第三方")
	private Integer loginType;
	/**
	 * ip地址
	 */
	@ApiModelProperty(value = "ip地址")
	private String ip;
	/**
	 * 设备号
	 */
	@ApiModelProperty(value = "设备号")
	private String deviceCode;

	@ApiModelProperty(value = "用户姓名")
	private String custName;

	@ApiModelProperty(value = "手机号")
	private String phoneNumber;

	@ApiModelProperty(value = "邮箱")
	private String email;

	@ApiModelProperty(value = "手机区号")
	private String areaCode;

	@ApiModelProperty(value = "手机区号")
	private String appVersion;

	@ApiModelProperty(value = "设备型号")
	private String deviceModel;

	@ApiModelProperty(value = "操作系统类型(0安卓，1苹果，2WP，4 windows)")
	private Integer osType;

	@ApiModelProperty(value = "请求源（web -WEB后台，ios -IOS端，aos -安卓端，pc -PC端，h5 -H5")
	private String sourceType;

	@ApiModelProperty(value = "记录时间")
	private Date createTime;

	@ApiModelProperty("国家中文名称")
	private String countryCnName;

	@ApiModelProperty("国家代码")
	private String countryCode;

	@ApiModelProperty("地区中文名称")
	private String regionCnName;

	@ApiModelProperty("地区码")
	private String regionCode;
}
