package com.minigod.zero.manage.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 渠道信息表
 *
 * @author eric
 * @date 2024-12-23 15:40:08
 * @TableName user_channel_info
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("user_channel_info")
public class UserChannelInfoEntity extends TenantEntity {

	/**
	 * 渠道ID
	 */
	private String channelId;

	/**
	 * 渠道名称
	 */
	private String channelName;

	/**
	 * 公司名称
	 */
	private String companyName;

	/**
	 * 预计接入点(1:首批)
	 */
	private String accessPoint;

	/**
	 * 导流方式（1:线上，2:线下）
	 */
	private String diversMode;

	/**
	 * 注册用户规模
	 */
	private Integer regisUserSize;

	/**
	 * 备注
	 */
	private String rmk;
}
