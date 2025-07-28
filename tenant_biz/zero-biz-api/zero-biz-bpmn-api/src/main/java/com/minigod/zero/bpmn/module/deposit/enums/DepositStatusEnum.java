package com.minigod.zero.bpmn.module.deposit.enums;

/**
 * @ClassName: com.minigod.zero.bpmn.module.deposit.enums.DepositStatusEnums
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/6/4 13:31
 * @Version: 1.0
 */
public class DepositStatusEnum {
	/**
	 * 流程状态 0-提交 1-审核 2-绑定 3-解绑 4-修改 99-结束
	 */
	public enum ApplicationStatus {
		COMMIT(0, "提交"),
		AUDIT(1, "审核"),
		BIND(2, "绑定"),
		UNBIND(3, "解绑"),
		MODIFY(4, "修改"),
		END(99, "结束");

		private ApplicationStatus(int code, String status) {
			this.code = code;
			this.status = status;
		}

		private int code;
		private String status;

		public int getCode() {
			return code;
		}

		public String getStatus() {
			return status;
		}

		public static String getApplicationStatus(int code) {
			for (DepositStatusEnum.ApplicationStatus statusEnum : DepositStatusEnum.ApplicationStatus.values()) {
				if (statusEnum.getCode() == code) {
					return statusEnum.getStatus();
				}
			}
			return null;
		}
	}


	public enum ApplicationFundTransStatus {
		COMMIT(0, "开始"),
		AUDIT1(1, "待初审"),
		AUDIT2(2, "待复审"),
		WITHDRAW(3, "待划转"),
		DEPOSIT(4, "待入账"),
		END(99, "结束");

		private ApplicationFundTransStatus(int code, String status) {
			this.code = code;
			this.status = status;
		}

		private int code;
		private String status;

		public int getCode() {
			return code;
		}

		public String getStatus() {
			return status;
		}

		public static String getApplicationStatus(int code) {
			for (DepositStatusEnum.ApplicationFundTransStatus statusEnum : DepositStatusEnum.ApplicationFundTransStatus.values()) {
				if (statusEnum.getCode() == code) {
					return statusEnum.getStatus();
				}
			}
			return null;
		}
	}

	/**
	 * 银行卡申请状态
	 */
	public enum BankCardApplicationStatus {

		/**
		 * 待审核
		 */
		NO_APPLY(0, "待审核"),
		/**
		 * 审核通过
		 */
		PENDING_AUDIT(1, "审核通过"),
		/**
		 * 审核不通过
		 */
		APPROVED(2, "审核不通过"),
		/**
		 * 取消
		 */
		REJECTED(3, "取消");;

		private int code;
		private String message;

		BankCardApplicationStatus(int code, String message) {
			this.code = code;
			this.message = message;
		}

		public int getCode() {
			return code;
		}

		public String getMessage() {
			return message;
		}

		public static String getMessage(int code) {
			for (DepositStatusEnum.BankCardApplicationStatus statusEnum : DepositStatusEnum.BankCardApplicationStatus.values()) {
				if (statusEnum.getCode() == code) {
					return statusEnum.getMessage();
				}
			}
			return null;
		}

	}

	/**
	 * 调拨申请状态
	 */
	public enum FundTransStatus {

		/**
		 * 待审核
		 */
		NO_APPLY(0, "待审核"),
		/**
		 * 审核通过
		 */
		PENDING_AUDIT(1, "审核通过"),
		/**
		 * 审核不通过
		 */
		APPROVED(2, "审核不通过"),

		/**
		 * 出金
		 */
		WITHDRAW(3, "划转"),

		/**
		 * 入金
		 */
		DEPOIST(4, "入账"),
		/**
		 * 取消
		 */
		REJECTED(3, "取消"),

		/**
		 * 完成
		 */
		FINISH(99, "已完成"),

		;

		private int code;
		private String message;

		FundTransStatus(int code, String message) {
			this.code = code;
			this.message = message;
		}

		public int getCode() {
			return code;
		}

		public String getMessage() {
			return message;
		}

		public static String getMessage(int code) {
			for (DepositStatusEnum.FundTransStatus statusEnum : DepositStatusEnum.FundTransStatus.values()) {
				if (statusEnum.getCode() == code) {
					return statusEnum.getMessage();
				}
			}
			return null;
		}

	}

	/**
	 * sec_deposit_funds 状态
	 * 状态 0已提交 1已受理 2已退回 3已完成 4已取消
	 */
	public enum SecDepositFundsStatus {
		COMMIT(0, "已提交"),
		ACCEPT(1, "已受理"),
		RETURN(2, "已退回"),
		FINISH(3, "已完成"),
		CANCEL(4, "已取消");

		private SecDepositFundsStatus(int code, String message) {
			this.code = code;
			this.message = message;
		}

		private int code;
		private String message;

		public int getCode() {
			return code;
		}

		public String getMessage() {
			return message;
		}
	}

	/**
	 * 银行卡类型[1-香港本地银行 2-中国大陆银行 3-海外银行]
	 */
	public enum BankCardType {
		HONGKONG(1, "香港本地银行"),
		MAINLAND(2, "中国大陆银行"),
		OVERSEAS(3, "海外银行");

		private BankCardType(int code, String type) {
			this.code = code;
			this.type = type;
		}

		private int code;
		private String type;

		public int getCode() {
			return code;
		}

		public String getType() {
			return type;
		}

		public static String getBankCardType(int code) {
			for (DepositStatusEnum.BankCardType bankCardType : DepositStatusEnum.BankCardType.values()) {
				if (bankCardType.getCode() == code) {
					return bankCardType.getType();
				}
			}
			return null;
		}
	}

	/**
	 * 币种
	 */
	public enum CurrencyType {
		HKD(1, "港币"),
		USD(2, "美元"),
		EUR(3, "人民币");

		private CurrencyType(int code, String currency) {
			this.code = code;
			this.currency = currency;
		}

		private int code;
		private String currency;

		public int getCode() {
			return code;
		}

		public String getCurrency() {
			return currency;
		}

		public static String getCurrency(int code) {
			for (DepositStatusEnum.CurrencyType currency : DepositStatusEnum.CurrencyType.values()) {
				if (currency.getCode() == code) {
					return currency.getCurrency();
				}
			}
			return null;
		}
	}

	/**
	 * 调拨划转方向枚举
	 */
	public enum FundTransDirections {
		S2F("STOCK_TO_FUND", "证券 to 基金"),
		F2S("FUND_TO_STOCK", "基金 to 证券"),
		;

		private FundTransDirections(String code, String desc) {
			this.code = code;
			this.desc = desc;
		}

		private String code;
		private String desc;

		public String getCode() {
			return code;
		}

		public String getDesc() {
			return desc;
		}

		public static String getDesc(String code) {
			for (DepositStatusEnum.FundTransDirections directions : DepositStatusEnum.FundTransDirections.values()) {
				if (directions.getCode().equals(code)) {
					return directions.getDesc();
				}
			}
			return "";
		}
	}
}
