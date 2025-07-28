package com.minigod.zero.bpm.enums;


import org.apache.commons.lang3.StringUtils;

public class OpenAccountEnum {
	/**
	 * 开户资料修改审核状态
	 */
	public enum OpenAccountModifyApproveStatus {
		APPROVE_PENDING(0, "待审核"),
		APPROVE_SUCCESS(1, "审核通过"),
		APPROVE_FAIL(2, "审核不通过");

		private OpenAccountModifyApproveStatus(Integer code, String message) {
			this.code = code;
			this.message = message;
		}

		private Integer code;
		private String message;

		public Integer getCode() {
			return code;
		}

		public void setCode(Integer code) {
			this.code = code;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public static OpenAccountModifyApproveStatus getOpenAccountModifyApproveStatus(Integer code) {
			for (OpenAccountModifyApproveStatus openAccountModifyApproveStatus : OpenAccountModifyApproveStatus.values()) {
				if (openAccountModifyApproveStatus.getCode().equals(code)) {
					return openAccountModifyApproveStatus;
				}
			}
			return null;
		}
	}


	/**
	 * 机构开户审核状态
	 */
	public enum OrganizationOpenApproveStatus {
		APPROVE_PENDING(0, "待审核"),
		APPROVE_SUCCESS(1, "审核通过"),
		APPROVE_FAIL(2, "审核不通过");

		private OrganizationOpenApproveStatus(Integer code, String message) {
			this.code = code;
			this.message = message;
		}

		private Integer code;
		private String message;

		public Integer getCode() {
			return code;
		}

		public void setCode(Integer code) {
			this.code = code;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public static OrganizationOpenApproveStatus getOrganizationOpenApproveStatus(Integer code) {
			for (OrganizationOpenApproveStatus organizationOpenApproveStatus : OrganizationOpenApproveStatus.values()) {
				if (organizationOpenApproveStatus.getCode().equals(code)) {
					return organizationOpenApproveStatus;
				}
			}
			return null;
		}
	}

	/**
	 * 机构开户状态
	 */
	public enum OrganizationOpenStatus {
		OPEN_PENDING(0, "待开户"),
		OPEN_SUCCESS(1, "开户成功"),
		OPEN_FAIL(2, "开户失败");

		private OrganizationOpenStatus(Integer code, String message) {
			this.code = code;
			this.message = message;
		}

		private Integer code;
		private String message;

		public Integer getCode() {
			return code;
		}

		public void setCode(Integer code) {
			this.code = code;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public static OrganizationOpenStatus getOrganizationOpenStatus(Integer code) {
			for (OrganizationOpenStatus organizationOpenStatus : OrganizationOpenStatus.values()) {
				if (organizationOpenStatus.getCode().equals(code)) {
					return organizationOpenStatus;
				}
			}
			return null;
		}
	}

	public enum OpenAccountStatus {
		OPEN_SUCCESS("0", "开户成功"),
		OPEN_DOING("1", "开户预审中"),
		OPEN_FAIL("2", "开户失败"),
		INFO_ERROR("3", "基本资料错误"),
		PIC_ERROR("4", "影像资料错误"),
		INFO_PIC_ERROR("5", "基本资料和影像资料错误"),
		N_COMMIT_INFO("6", "未提交开户资料"),
		OPEN_REJECTED("7", "开户被拒绝"),
		OPEN_CANCAL("8", "取消开户"),
		FINAL_REVIEW("9", "终审审核通过"),
		PRE_APPROVAL("10", "开户预批中"),
		ACCOUNT_CANCELLATION("11", "销户");

		OpenAccountStatus(String code, String message) {
			this.code = code;
			this.message = message;
		}

		public static OpenAccountStatus getOpenAccountStatus(String code) {
			if (StringUtils.isBlank(code)) return null;
			for (OpenAccountStatus openAccountStatus : OpenAccountStatus.values()) {
				if (openAccountStatus.getCode().equals(code)) {
					return openAccountStatus;
				}
			}
			return null;
		}

		private String code;
		private String message;

		public String getCode() {
			return this.code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getMessage() {
			return this.message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}

	public enum OpenAccountType {
		ONLINE(1, "线上开户"),
		OFFLINE(2, "线下开户"),
		HK_OPEN(3, "香港开户");

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

	public enum OpenAccountAccessWay {
		H5(1, "线上H5开户"),
		APP(2, "线上APP开户"),
		OFFLINE(3, "线下开户");

		private OpenAccountAccessWay(int code, String message) {
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

		public static OpenAccountAccessWay getNameByCode(int code) {
			for (OpenAccountAccessWay openAccountAccessWay : OpenAccountAccessWay.values()) {
				if (openAccountAccessWay.getCode() == code) {
					return openAccountAccessWay;
				}
			}
			return null;
		}
	}

	public enum OpenStatus {
		OPEN_DONE(0, "已开户"),
		OPEN_DOING(1, "开户中"),
		REFUSED_FAIL(2, "已退回"),
		REGISTER_DONE(3, "已注册"),
		TRADE(4, "已交易"),
		DEPOSIT(5, "已入金");

		private OpenStatus(int code, String message) {
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

	public enum OpenApplyStatus {
		NO_DREDGE(0, "未开通"),
		DREDGE(1, "已开通"),
		CHECKING(2, "审核中"),
		FAIL_DREDGE(3, "开通失败");

		private OpenApplyStatus(int code, String message) {
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

	public enum OpenResultInfo {
		BACK_ONE("1", "证照信息与填写内容不符"),
		BACK_TWO("2", "资金来源"),
		BACK_THREE("3", "银行帐户有误"),
		BACK_FOUR("4", "衍生品金融机构经验"),
		BACK_FIVE("5", "年龄"),
		BACK_SIX("6", "衍生品与股票经验不一致"),
		BACK_SEVEN("7", "就业信息"),
		BACK_EIGHT("8", "年收入"),
		BACK_NINE("9", "公司名称"),
		BACK_TEN("10", "财产种类"),
		BACK_ELEVEN("11", "所属行业"),
		BACK_TWELVE("12", "受益人类型"),
		BACK_THIRTEEN("13", "身份证有效期限"),
		BACK_FOURTEEN("14", "与yff集团关系"),
		BACK_FIFTEEN("15", "身份证地址"),
		BACK_SIXTEEN("16", "证件会注册"),
		BACK_SEVENTEEN("17", "其他原因"),
		BACK_EIGHTEEN("18", "投资经验");

		private OpenResultInfo(String code, String message) {
			this.code = code;
			this.mesage = message;
		}

		private String code;
		private String mesage;

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getMesage() {
			return mesage;
		}

		public void setMesage(String mesage) {
			this.mesage = mesage;
		}

		public static OpenResultInfo getBackResult(String code) {
			for (OpenResultInfo c : values()) {
				if (code.equals(c.getCode())) {
					return c;
				}
			}
			return null;
		}
	}


	public enum BpmClientStatus {
		STS_0("0", "正常"),
		STS_1("1", "冻结"),
		STS_2("2", "挂失"),
		STS_3("3", "销户"),
		STS_D("D", "休眠"),
		STS_E("E", "不合格"),
		STS_F("F", "锁定");
		public String value;
		public String description;

		BpmClientStatus(String value, String description) {
			this.value = value;
			this.description = description;
		}
	}

	public enum BpmAcctType {
		ACCT_TYPE_1(1, "个人"),
		ACCT_TYPE_2(2, "联名"),
		ACCT_TYPE_3(3, "公司");
		public Integer value;
		public String description;

		BpmAcctType(Integer value, String description) {
			this.value = value;
			this.description = description;
		}
	}

	/**
	 * 更新状态枚举类
	 *
	 * @param
	 * @return
	 */
	public enum UpdateStatusType {
		UPDATE_PENDING(0, "待更新"),
		UPDATE_SUCCESS(1, "更新成功"),
		UPDATE_FAIL(2, "更新失败");
		public Integer code;
		public String desc;

		UpdateStatusType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

		public Integer getCode() {
			return code;
		}

		public void setCode(Integer code) {
			this.code = code;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}
	}

	/**
	 * 同步状态枚举
	 *
	 * @param
	 * @return
	 */
	public enum SyncStatusType {
		SYNC_PENDING(0, "待同步"),
		SYNC_SUCCESS(1, "同步成功"),
		SYNC_FAIL(2, "同步失败");
		public Integer code;
		public String desc;

		SyncStatusType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

		public Integer getCode() {
			return code;
		}

		public void setCode(Integer code) {
			this.code = code;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}
	}
}
