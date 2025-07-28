package com.minigod.zero.platform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

import lombok.EqualsAndHashCode;

/**
 * 系统通知信息表 实体类
 *
 * @author 掌上智珠
 * @since 2023-03-11
 */
@Data
@TableName("platform_sys_msg")
@ApiModel(value = "PlatformSysMsg对象", description = "系统通知信息表")
@EqualsAndHashCode(callSuper = true)
public class PlatformSysMsgEntity extends TenantEntity {

    /**
     * 消息类型(A:活动 R:提醒 N:公告 X:要闻 B:播报)
     */
    @ApiModelProperty(value = "消息类型(A:活动 R:提醒 N:公告 X:要闻 B:播报)")
    private String msgType;
    /**
     * 消息级别(I:重要 G:普通)
     */
    @ApiModelProperty(value = "消息级别(I:重要 G:普通)")
    private String msgLevel;
    /**
     * 通知模板编码
     */
    @ApiModelProperty(value = "通知模板编码")
    private Integer tempCode;
    /**
     * 标题-简体
     */
    @ApiModelProperty(value = "标题-简体")
    private String title;
    /**
     * 标题-英语
     */
    @ApiModelProperty(value = "标题-英语")
    private String titleEn;
    /**
     * 标题-繁体
     */
    @ApiModelProperty(value = "标题-繁体")
    private String titleHant;
    /**
     * 内容-简体
     */
    @ApiModelProperty(value = "内容-简体")
    private String content;
    /**
     * 内容-繁体
     */
    @ApiModelProperty(value = "内容-繁体")
    private String contentHant;
    /**
     * 内容-英语
     */
    @ApiModelProperty(value = "内容-英语")
    private String contentEn;
    /**
     * 客户端类型(0-全部终端 1-Android 2-IOS)
     */
    @ApiModelProperty(value = "客户端类型(0-全部终端 1-Android 2-IOS)")
    private Integer clientType;
    /**
     * 消息分组(P-个人，A-全站，L-标签用户，T-用户分组)
     */
    @ApiModelProperty(value = "消息分组(P-个人，A-全站，L-标签用户，T-用户分组)")
    private String msgGroup;
    /**
     * msg_group=P时,记录为用户id;当msg_group=A时,为0
     */
    @ApiModelProperty(value = "msg_group=P时,记录为用户id;当msg_group=A时,为0")
    private Long targetId;
    /**
     * 推送类型(0-强消息 1-弱消息)
     */
    @ApiModelProperty(value = "推送类型(0-强消息 1-弱消息)")
    private Integer sendType;
    /**
     * 推送方式(0-即时 1-定时)
     */
    @ApiModelProperty(value = "推送方式(0-即时 1-定时)")
    private Integer sendWay;
    /**
     * 推送时间,推送方式为定时时,不为空
     */
    @ApiModelProperty(value = "推送时间,推送方式为定时时,不为空")
    private Date sendTime;
    /**
     * 推送状态(0-未发送，1-已发送 2-发送失败)
     */
    @ApiModelProperty(value = "推送状态(0-未发送，1-已发送 2-发送失败)")
    private Integer sendStatus;
    /**
     * 重发次数
     */
    @ApiModelProperty(value = "重发次数")
    private Integer retryCnt;
    /**
     * 权限审核人ID
     */
    @ApiModelProperty(value = "权限审核人ID")
    private Long oprId;
    /**
     * 权限审核人名称
     */
    @ApiModelProperty(value = "权限审核人名称")
    private String oprName;
    /**
     * 跳转页面地址
     */
    @ApiModelProperty(value = "跳转页面地址")
    private String url;
    /**
     * 更新版本号
     */
    @ApiModelProperty(value = "更新版本号")
    private Long updVersion;
	/**
	 * 是否弹窗 1-是 0-否
	 */
	@ApiModelProperty(value = "是否弹窗 1-是 0-否")
	private Integer popFlag;
	/**
	 * 弹窗失效时间
	 */
	@ApiModelProperty(value = "弹窗失效时间")
	private Date popInvalidTime;

}
