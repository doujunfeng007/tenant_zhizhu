package com.minigod.zero.manage.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 活动用户信息采集表
 *
 * @author eric
 * @date 2024-12-23 17:00:08
 * @TableName sn_activity_user_collect
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sn_activity_user_collect")
public class SnActivityUserCollectEntity extends TenantEntity {

	/**
	 * 用户id
	 */
	private Integer userId;

	/**
	 * 开户状态0未开户，1已开户
	 */
	private String accountOpenStatus;

	/**
	 * 开户时间
	 */
	private LocalDateTime accountOpenTime;

	/**
	 * 交易账号
	 */
	private String tradeAccount;

	/**
	 * 资金账号
	 */
	private String fundAccount;

	/**
	 * 首次入金金额
	 */
	private BigDecimal firstDepositAmount;

	/**
	 * 首次入金时间
	 */
	private LocalDateTime firstDepositTime;

	/**
	 * 首次转入股票金额
	 */
	private BigDecimal firstTransferAmount;

	/**
	 * 首次转入股票时间
	 */
	private LocalDateTime firstTransferTime;

	/**
	 * 交易状态 0=未交易，1=已交易
	 */
	private Integer tradeStatus;

	/**
	 * 最近一次出金时间
	 */
	private LocalDateTime lastWithdrawalTime;

	/**
	 * 最近一次转出股票时间
	 */
	private LocalDateTime lastTransferOutTime;

	/**
	 * 锁版本号
	 */
	@Version
	private Integer lockVersion;

	/**
	 * 客户ID
	 */
	private Integer clientId;

	/**
	 * 客户理财账号
	 */
	private Integer accountId;

	/**
	 * 姓名
	 */
	private String clientName;

	/**
	 * 客户手机号码
	 */
	private String clientPhoneNumber;

	/**
	 * 客户邮箱
	 */
	private String clientEmail;
}
