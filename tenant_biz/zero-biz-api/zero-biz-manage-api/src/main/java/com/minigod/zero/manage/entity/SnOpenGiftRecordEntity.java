package com.minigod.zero.manage.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 开放渠道礼包记录
 *
 * @author eric
 * @date 2024-12-23 15:30:08
 * @TableName sn_open_gift_record
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sn_open_gift_record")
public class SnOpenGiftRecordEntity extends TenantEntity {

	/**
	 * 用户ID
	 */
	private Integer userId;

	/**
	 * 操作人员ID
	 */
	private Integer oprId;

	/**
	 * 操作人员名称
	 */
	private String oprName;

	/**
	 * 申请原因
	 */
	private String oprReason;
}
