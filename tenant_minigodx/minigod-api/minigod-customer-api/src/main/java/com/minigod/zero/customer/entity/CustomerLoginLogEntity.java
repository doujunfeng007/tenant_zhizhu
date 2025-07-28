package com.minigod.zero.customer.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.lang.Boolean;
import java.util.Date;
import lombok.EqualsAndHashCode;
import com.minigod.zero.core.mp.base.BaseEntity;

/**
 * 登陆日志表 实体类
 *
 * @author ken
 * @since 2024-04-11
 */
@Data
@TableName("customer_login_log")
@ApiModel(value = "CustomerLoginLog对象", description = "登陆日志表")
@EqualsAndHashCode(callSuper = true)
public class CustomerLoginLogEntity extends BaseEntity {
	/**
	 * 租户id
	 */
	@ApiModelProperty(value = "租户id")
	private String tenantId;
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
     * 类型 1-客户登录，2-交易解锁，3-异地访问
     */
    @ApiModelProperty(value = "类型 1-客户登录，2-交易解锁，3-异地访问")
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
    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String email;
    /**
     * 用户姓名
     */
    @ApiModelProperty(value = "用户姓名")
    private String custName;
    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String phoneNumber;
    /**
     * 手机区号
     */
    @ApiModelProperty(value = "手机区号")
    private String areaCode;
    /**
     * app版本
     */
    @ApiModelProperty(value = "app版本")
    private String appVersion;
    /**
     * 设备型号
     */
    @ApiModelProperty(value = "设备型号")
    private String deviceModel;
    /**
     * 操作系统类型(0安卓，1苹果，2WP，4 windows)
     */
    @ApiModelProperty(value = "操作系统类型(0安卓，1苹果，2WP，4 windows)")
    private Integer osType;
    /**
     * 请求源
     */
    @ApiModelProperty(value = "请求源")
    private String sourceType;
    /**
     * 国家中文名称
     */
    @ApiModelProperty(value = "国家中文名称")
    private String countryCnName;
    /**
     * 国家代码
     */
    @ApiModelProperty(value = "国家代码 ")
    private String countryCode;
    /**
     * 地区名称
     */
    @ApiModelProperty(value = "地区名称")
    private String regionCnName;
    /**
     * 地区码
     */
    @ApiModelProperty(value = "地区码")
    private String regionCode;

}
