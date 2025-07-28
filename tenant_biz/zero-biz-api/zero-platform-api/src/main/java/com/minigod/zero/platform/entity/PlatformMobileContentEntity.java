package com.minigod.zero.platform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 短信内容信息表
 *
 * @author 掌上智珠
 */
@Data
@TableName("platform_mobile_content")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "短信内容信息表", description = "短信内容信息表")
public class PlatformMobileContentEntity extends TenantEntity {

	private static final long serialVersionUID = 1L;

	/** 模板编码 */
	@ApiModelProperty(value = "模板编码")
	private Integer tempCode;

	/** 手机号码 */
	@ApiModelProperty(value = "手机号码")
	private String cellPhone;

	/** 短信标题 */
	@ApiModelProperty(value = "短信标题")
	private String title;

	/** 短信内容 */
	@ApiModelProperty(value = "短信内容")
	private String content;

	/** 描述 */
	@ApiModelProperty(value = "描述")
	private String discription;

	/** 0-创蓝 1-阿里云 */
	@ApiModelProperty(value = "短信渠道(0-创蓝 1-阿里云)")
	private Integer channel;

	/** 推送状态(0-未发送，1-已发送 2-发送失败) */
	@ApiModelProperty(value = "推送状态(0-未发送，1-已发送 2-发送失败)")
	private Integer sendStatus;

	/** 发送类型(0-即时 1-定时) */
	@ApiModelProperty(value = "发送类型(0-即时 1-定时)")
	private Integer sendType;

	/** 定时发送时间 */
	@ApiModelProperty(value = "定时发送时间")
	private Date timing;

	/** 失败次数 */
	@ApiModelProperty(value = "失败次数")
	private Integer failCnt = 0;

	/** 重发次数 */
	@ApiModelProperty(value = "重发次数")
	private Integer retryCnt = 0;

	/** 发送时间 */
	@ApiModelProperty(value = "发送时间")
	private Date sendTime;

	/** 接收时间 */
	@ApiModelProperty(value = "接收时间")
	private Date receiveTime;

	/** 权限审核人ID */
	@ApiModelProperty(value = "权限审核人ID")
	private Integer oprId;

	/** 权限审核人名称 */
	@ApiModelProperty(value = "权限审核人名称")
	private String oprName;

	/** 短信类型 */
	@ApiModelProperty(value = "短信类型")
	private Integer msgType = 0;

	/** 语言 */
	@ApiModelProperty(value = "语言")
	private String lang;

	/** 错误信息 */
	@ApiModelProperty(value = "错误信息")
	private String errorMsg;

	/** 唯一id_服务商消息id */
	@ApiModelProperty(value = "唯一id_服务商消息id")
	private String smsId;

	/**
	 * 本地消息id
	 */
	private String msgId;

}
