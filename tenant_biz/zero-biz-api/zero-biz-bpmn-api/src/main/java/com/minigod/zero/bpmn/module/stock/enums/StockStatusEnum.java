package com.minigod.zero.bpmn.module.stock.enums;

import com.minigod.zero.bpmn.module.deposit.enums.DepositStatusEnum;

public class StockStatusEnum {

	/**
	 * 流程状态 0-受理 1-审核 2-同步柜台 99-结束
	 */
	public enum ApplicationStatus {
		COMMIT(0, "受理"),
		APPROVE(1, "审核"),
		OPEN(2, "开户"),
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

	/**
	 * 增开股票审核状态
	 */
	public enum OpenAccountModifyApproveStatus {
		APPROVE_PENDING(0, "待审核"),
		APPROVE_SUCCESS(1, "审核通过"),
		APPROVE_FAIL(2, "审核不通过");

		private OpenAccountModifyApproveStatus(Integer code, String message) {
			this.code = code;
			this.message = message;
		}

		private int code;
		private String message;

		OpenAccountModifyApproveStatus(int code, String message) {
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

	public enum OpenAccountType {
		ONLINE(1, "线上开户"),
		OFFLINE(2, "线下开户");

		private OpenAccountType(int code, String message) {
			this.code = code;
			this.message = message;
		}

		private int code;
		private String message;

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}

	public enum FundAccountType {
		CASH(1, "现金账户"),
		MARGIN(2, "融资账户");

		private FundAccountType(int code, String message) {
			this.code = code;
			this.message = message;
		}

		private int code;
		private String message;

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}
}
