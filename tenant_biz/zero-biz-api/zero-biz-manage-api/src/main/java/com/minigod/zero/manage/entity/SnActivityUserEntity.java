package com.minigod.zero.manage.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 活动用户表
 *
 * @author eric
 * @date 2024-12-23 15:10:08
 * @TableName sn_activity_user
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sn_activity_user")
public class SnActivityUserEntity extends TenantEntity {

	/**
	 * 用户id
	 */
	private Integer userId;

	/**
	 * 用户昵称
	 */
	private String nickName;

	/**
	 * 注册渠道
	 */
	private String channel;

	/**
	 * 邀请码
	 */
	private String inviteCode;

	/**
	 * 邀请人ID
	 */
	private Integer inviteIdentify;

	/**
	 * 注册时间
	 */
	private LocalDateTime registerTime;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 姓名
	 */
	private String clientName;

	/**
	 * 头像
	 */
	private String iconUrl;

	/**
	 * 手机号
	 */
	private String phoneNumber;

	/**
	 * 乐观锁版本号
	 */
	private Integer lockVersion;

	/**
	 * 注册来源
	 */
	private String utmSource;

	/**
	 * 来源标识
	 */
	private String utmMedium;

	/**
	 * 微信OpenId
	 */
	private String openId;
}
