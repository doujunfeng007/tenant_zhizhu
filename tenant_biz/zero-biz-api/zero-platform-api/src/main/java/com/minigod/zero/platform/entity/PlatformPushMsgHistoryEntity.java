package com.minigod.zero.platform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 推送消息历史表 实体类
 *
 * @author 掌上智珠
 * @since 2023-02-27
 */
@Data
@TableName("platform_push_msg_history")
@ApiModel(value = "PushMsgHistory对象", description = "推送消息历史表")
@EqualsAndHashCode(callSuper = true)
public class PlatformPushMsgHistoryEntity extends TenantEntity {
    /**
     * 模板编码
     */
    @ApiModelProperty(value = "模板编码")
    private Integer tempCode;
    /**
     * 客户端类型(0-安卓，1-IOS，2-WP系统，3-全部)
     */
    @ApiModelProperty(value = "客户端类型(0-安卓，1-IOS，2-WP系统，3-全部)")
    private Integer osType;
    /**
     * 消息分组(P-个人，A-全站，L-标签用户，T-用户分组)
     */
    @ApiModelProperty(value = "消息分组(P-个人，A-全站，L-标签用户，T-用户分组)")
    private String msgGroup;
    /**
     * 用户id
     */
    private Long targetId;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private String content;
    /**
     * 推送状态(0-未发送 1-发送成功 2-发送失败)
     */
    @ApiModelProperty(value = "推送状态(0-未发送 1-发送成功 2-发送失败)")
    private Integer sendStatus;
    /**
     * 发送时间
     */
    @ApiModelProperty(value = "发送时间")
    private Date sendTime;
    /**
     * 关联id
     */
    @ApiModelProperty(value = "关联id")
    private String relationId;

	/**
	 * 本地消息id
	 */
	private String msgId;

	/**
	 * {@link com.minigod.zero.platform.enums.PushChannel}
	 */
	private Integer channel;
	/**
	 * 推送设备号
	 */
	private String deviceId;
}
