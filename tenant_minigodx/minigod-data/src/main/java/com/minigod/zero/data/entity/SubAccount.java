package com.minigod.zero.data.entity;


import lombok.Data;

import java.time.LocalDateTime;

/**
 * 子账户实体类
 *
 * @author eric
 * @date 2024-10-28 16:07:08
 */
@Data
public class SubAccount {
	/**
	 * 子账户ID
	 */
	private String subAccountId;

	/**
	 * 主账户ID
	 */
	private String accountId;

	/**
	 * 账户状态
	 * active: 有效
	 * inactive: 无效
	 */
	private String status;

	/**
	 * 账户类型
	 * GIA: General Investment Account (通用投资账户)
	 */
	private String subAccountType;

	/**
	 * 创建时间
	 */
	private LocalDateTime createDate;

	/**
	 * 费率角色ID
	 * 默认值: 2
	 */
	private Integer feeRoleId;

	/**
	 * 用户费率生效时间
	 */
	private LocalDateTime feeRateBeginDate;

	/**
	 * 用户费率失效时间
	 */
	private LocalDateTime feeRateEndDate;

	/**
	 * 获取账户状态的枚举值
	 */
	public enum Status {
		/**
		 * 有效状态
		 */
		ACTIVE("active"),

		/**
		 * 无效状态
		 */
		INACTIVE("inactive");

		private final String value;

		Status(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	/**
	 * 获取账户类型的枚举值
	 */
	public enum SubAccountType {
		/**
		 * 通用投资账户
		 */
		GIA("GIA");

		private final String value;

		SubAccountType(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
}
