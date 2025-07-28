package com.minigod.zero.platform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 邮件内容信息表 实体类
 *
 * @author 掌上智珠
 * @since 2023-02-24
 */
@Data
@TableName("platform_email_content")
@ApiModel(value = "InformEmailContent对象", description = "邮件内容信息表")
@EqualsAndHashCode(callSuper = true)
public class PlatformEmailContentEntity extends TenantEntity {
    /**
     * 模板编码
     */
    @ApiModelProperty(value = "模板编码")
    private Integer tempCode;
    /**
     * 邮件标题
     */
    @ApiModelProperty(value = "邮件标题")
    private String title;
    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    private String content;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;
    /**
     * 邮件地址
     */
    @ApiModelProperty(value = "邮件地址")
    private String address;
    /**
     * 推送状态(0-未发送，1-已发送 2-发送失败)
     */
	@ApiModelProperty(value = "推送状态(0-未发送，1-已发送 2-发送失败 3-发送中)")
	private Integer sendStatus;
    /**
     * 发送类型(-即时 1-定时)
     */
    @ApiModelProperty(value = "发送类型(-即时 1-定时)")
    private Integer sendType;
    /**
     * 定时发送时间
     */
    @ApiModelProperty(value = "定时发送时间")
    private Date sendTime;
    /**
     * 重发次数
     */
    @ApiModelProperty(value = "重发次数")
    private Integer retryCnt;
    /**
     * 是否有附件(0-否 1-是)
     */
    @ApiModelProperty(value = "是否有附件(0-否 1-是)")
    private Integer hasAttach;
    /**
     * 附件地址
     */
    @ApiModelProperty(value = "附件地址")
    private String attachUris;
    /**
     * 权限审核人ID
     */
    @ApiModelProperty(value = "权限审核人ID")
    private Integer oprId;
    /**
     * 权限审核人名称
     */
    @ApiModelProperty(value = "权限审核人名称")
    private String oprName;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private Integer deviceId;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private String loginIp;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private Integer userId;
    /**
     * 唯一ID，外部系统调用发送消息传入
     */
    @ApiModelProperty(value = "唯一ID，外部系统调用发送消息传入")
    private String sendId;
	/**
	 * 密抄送人
	 */
	@ApiModelProperty(value = "密抄送人")
	private String blindCarbonCopy;
    /**
     * 抄送人
     */
    @ApiModelProperty(value = "抄送人")
    private String carbonCopy;
	/**
	 * 错误信息
	 */
	@ApiModelProperty(value = "错误信息")
	private String errorMsg;
	/**
	 * 发出时间
	 */
	@ApiModelProperty(value = "发出时间")
	private Date deliveryTime;
    /**
     * 发送人
     */
    @ApiModelProperty(value = "发送人")
    private String sender;

	/**
	 * 发送人名称
	 */
	@ApiModelProperty(value = "发送人名称")
	private String fromName;

	/**
	 * sendCloud邮件ID
	 * to = [A, B, C] 邮件地址
	 * emailId_A = messageId + to.index(A) + '$' + A
	 */
	@ApiModelProperty(value = "sendCloud邮件ID")
	private String emailId;

	/**
	 * sendCloud邮件投递状态
	 */
	@ApiModelProperty(value = "sendCloud邮件投递状态")
	private String sendcloudStatus;

	/**
	 * 发送smtp状态(0-未发送，1-已发送，2-发送失败)
	 */
	@ApiModelProperty(value = "发送smtp状态(0-未发送，1-已发送，2-发送失败)")
	private Integer sendsmtpStatus;

	/**
	 * 本地消息id
	 */
	private String msgId;

	/**
	 * 发送渠道
	 * {@link com.minigod.zero.platform.enums.EmailChannel}
	 */
	private Integer channel;

}
