package com.minigod.zero.platform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 推送消息表 实体类
 *
 * @author 掌上智珠
 * @since 2023-02-25
 */
@Data
@TableName("platform_invest_msg")
@ApiModel(value = "InvestMsg对象", description = "推送消息表")
@EqualsAndHashCode(callSuper = true)
public class PlatformInvestMsgEntity extends TenantEntity {
    /**
     *
     */
    @ApiModelProperty(value = "")
    private Integer ptfId;
    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    private Long userId;
    /**
     * 发表人ID
     */
    @ApiModelProperty(value = "发表人ID")
    private Long fromUser;
    /**
     * 消息类型
     */
    @ApiModelProperty(value = "消息类型")
    private String msgType;
    /**
     * 消息显示分组
     */
    @ApiModelProperty(value = "消息显示分组【12015-交易提醒 13001-行情提醒 13002-新股提醒 12007-服务通知 13003-活动推送 13004-热点资讯】")
    private Integer displayGroup;
	/**
	 * 扩展模板代码
	 */
	@ApiModelProperty(value = "扩展模板代码")
	private Integer msgCode;
    /**
     * 消息标题
     */
    @ApiModelProperty(value = "消息标题")
    private String title;
	/**
	 * 消息标题繁体
	 */
	@ApiModelProperty(value = "消息标题繁体")
	private String titleHant;
	/**
	 * 消息标题英文
	 */
	@ApiModelProperty(value = "消息标题英文")
	private String titleEn;
    /**
	 * 消息内容
	 */
	@ApiModelProperty(value = "消息内容")
	private String msgContent;
	/**
	 * 消息内容繁体
	 */
	@ApiModelProperty(value = "消息内容繁体")
	private String msgContentHant;
	/**
	 * 消息内容英文
	 */
	@ApiModelProperty(value = "消息内容英文")
	private String msgContentEn;
    /**
     * 关联对象类型
     */
    @ApiModelProperty(value = "关联对象类型")
    private String relaType;
    /**
     * 关联对象ID
     */
    @ApiModelProperty(value = "关联对象ID")
    private Long relaId;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private String relaText;
    /**
     * 源表类型
     */
    @ApiModelProperty(value = "源表类型")
    private String srcType;
    /**
     * 源表ID
     */
    @ApiModelProperty(value = "源表ID")
    private Integer srcId;
    /**
     * 更新版本号
     */
    @ApiModelProperty(value = "更新版本号")
    private Long updVersion;
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
     * 消息分组(P-个人，A-全站，L-标签用户，T-用户分组)
     */
    @ApiModelProperty(value = "消息分组(P-个人，A-全站，L-标签用户，T-用户分组)")
    private String msgGroup;
    /**
     * 客户端类型(0-全部 1-安卓 2-IOS)
     */
    @ApiModelProperty(value = "客户端类型(0-全部 1-安卓 2-IOS)")
    private Integer clientType;
    /**
     * 扩展字段
     */
    @ApiModelProperty(value = "扩展字段")
    private String extend;
    /**
     * 唯一ID，外部系统调用发送消息传入
     */
    @ApiModelProperty(value = "唯一ID，外部系统调用发送消息传入")
    private String sendId;
    /**
     * 推送跳转链接
     */
    @ApiModelProperty(value = "推送跳转链接")
    private String url;

	private String tenantId;

}
