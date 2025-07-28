package com.minigod.zero.cust.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 客户设备信息 实体类
 *
 * @author 掌上智珠
 * @since 2023-02-08
 */
@Data
@TableName("cust_device")
@ApiModel(value = "CustDevice对象", description = "客户设备信息")
@EqualsAndHashCode(callSuper = true)
public class CustDeviceEntity extends BaseEntity {

    /**
     * 客户ID
     */
    @ApiModelProperty(value = "客户ID")
    private Long custId;
    /**
     * 当为游客时值为0，1-非游客
     */
    @ApiModelProperty(value = "当为游客时值为0，1-非游客")
    private Integer guestFlag;
    /**
     * 设备类型(0PC;1手机;2平板 3WEB)
     */
    @ApiModelProperty(value = "设备类型(0PC;1手机;2平板 3WEB)")
    private Integer deviceType;
    /**
     * 设备型号
     */
    @ApiModelProperty(value = "设备型号(如 SM-A5160 /Iphone 14 pro)")
    private String deviceModel;
    /**
     * 操作系统类型(0安卓，1苹果，2WP，4 windows)
     */
    @ApiModelProperty(value = "操作系统类型(0安卓，1苹果，2WP，4 windows)")
    private Integer osType;
    /**
     * 操作系统版本
     */
    @ApiModelProperty(value = "操作系统版本 (如 安卓 REL  IOS的 15.4.1)")
    private String osVersion;
    /**
     * app版本
     */
    @ApiModelProperty(value = "app版本")
    private String appVersion;
    /**
     * 设备名称
     */
    @ApiModelProperty(value = "设备名称 (如 huawei /iphone)")
    private String deviceName;
    /**
     * 设备号
     */
    @ApiModelProperty(value = "设备号")
    private String deviceCode;
    /**
     * 开户token
     */
    @ApiModelProperty(value = "开户token")
    private String openToken;
    /**
     * 极光ID
     */
    @ApiModelProperty(value = "极光ID")
    private String openCode;
    /**
     * 当前使用的渠道
     */
    @ApiModelProperty(value = "当前使用的渠道")
    private String channel;
    /**
     * 认证标识 0否 1 是
     */
    @ApiModelProperty(value = "认证标识 0否 1 是")
    private String authFlag;
    /**
     * 0表示关闭了通知，1(1&lt;&lt;0)表示开启了应用图标提醒，2(1&lt;&lt;1)表示开启声音提醒，4(1&lt;&lt;2)表示开启弹窗提醒 8(1&lt;&lt;3)不用管。复选的时候数字简单相加。默认为-1，表示未知。
     */
    @ApiModelProperty(value = "0表示关闭了通知，1(1&lt;&lt;0)表示开启了应用图标提醒，2(1&lt;&lt;1)表示开启声音提醒，4(1&lt;&lt;2)表示开启弹窗提醒 8(1&lt;&lt;3)不用管。复选的时候数字简单相加。默认为-1，表示未知。")
    private String pushConfig;
    /**
     * APP端系统推送设置信息更新时间。默认为空，push_config字段更新时更新
     */
    @ApiModelProperty(value = "APP端系统推送设置信息更新时间。默认为空，push_config字段更新时更新")
    private Date configUpdateTime;
	/**
	 * 语言
	 */
	@ApiModelProperty(value = "语言")
	private String lang;
}
