package com.minigod.zero.manage.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 活动配置表
 *
 * @author eric
 * @date 2024-12-23 14:45:08
 * @TableName sn_active_config
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sn_active_config")
public class SnActiveConfigEntity extends TenantEntity {

	/**
	 * 活动名称
	 */
	private String activeName;

	/**
	 * 活动类型：1、开户 2、入金 3、转仓
	 */
	private Integer activeType;

	/**
	 * 活动开始日期
	 */
	private LocalDateTime startTime;

	/**
	 * 活动结束日期
	 */
	private LocalDateTime endTime;

	/**
	 * 活动备注
	 */
	private String remark;

	/**
	 * 记录状态 0有效 1失效
	 */
	private Integer status;
}
