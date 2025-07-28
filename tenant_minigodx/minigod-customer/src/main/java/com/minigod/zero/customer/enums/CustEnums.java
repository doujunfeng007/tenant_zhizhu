package com.minigod.zero.customer.enums;

import com.minigod.zero.core.tool.utils.StringUtil;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/5/7 13:50
 * @description：
 */
public interface CustEnums {

	enum LoginWayType {
		REGISTER(1, "手机号", "register"),
		CORP_AUTHOR(2, "公司授权人", "corp_author"),
		ACCOUNT(3, "用户号", "account"),
		TRADE_ACCT(4, "交易账号", "trade_acct"),
		SOCIAL(5, "第三方", "social"),
		ESOP_ACCT(6, "ESOP户", "esop_acct"),
		UNKNOWN(9, "未知", "unknown");

		private final int code;
		private final String desc;
		private final String grantType;

		private LoginWayType(int code, String desc, String grantType) {
			this.code = code;
			this.desc = desc;
			this.grantType = grantType;
		}

		public int getCode() {
			return this.code;
		}

		public String getDesc() {
			return this.desc;
		}

		public String getGrantType() {
			return this.grantType;
		}

		public static int getCode(String grantType) {
			if (StringUtil.isBlank(grantType)) {
				return UNKNOWN.code;
			} else {
				CustEnums.LoginWayType[] var1 = values();
				int var2 = var1.length;

				for(int var3 = 0; var3 < var2; ++var3) {
					CustEnums.LoginWayType loginWayType = var1[var3];
					if (loginWayType.grantType.equalsIgnoreCase(grantType)) {
						return loginWayType.getCode();
					}
				}

				return UNKNOWN.code;
			}
		}

		public static CustEnums.LoginWayType of(String grantType) {
			if (StringUtil.isBlank(grantType)) {
				return UNKNOWN;
			} else {
				CustEnums.LoginWayType[] var1 = values();
				int var2 = var1.length;

				for(int var3 = 0; var3 < var2; ++var3) {
					CustEnums.LoginWayType loginWayType = var1[var3];
					if (loginWayType.grantType.equalsIgnoreCase(grantType)) {
						return loginWayType;
					}
				}

				return UNKNOWN;
			}
		}
	}

	enum LoginType {
		UIN(1, "用户号"),
		PHONE(2, "手机号"),
		EMAIL(3, "邮箱");

		private final int code;
		private final String desc;

		private LoginType(int code, String desc) {
			this.code = code;
			this.desc = desc;
		}

		public int getCode() {
			return this.code;
		}

		public String getDesc() {
			return this.desc;
		}
	}

	enum AcctStatus {
		NORMAL("0", "正常"),
		FREEZE("1", "冻结"),
		LOSS("2", "挂失"),
		CANCEL("3", "销户"),
		DORMANT("D", "休眠"),
		ELIMINATE("E", "不合格"),
		LOCK("F", "锁定");

		private final String code;
		private final String desc;

		private AcctStatus(String code, String desc) {
			this.code = code;
			this.desc = desc;
		}

		public String getCode() {
			return this.code;
		}

		public String getDesc() {
			return this.desc;
		}
	}

	enum AcctType {
		PERSON(1, "个人"),
		JOINTLY(2, "联名"),
		CORP(3, "公司"),
		ESOP(4, "EKEEPER");

		private final int code;
		private final String desc;

		private AcctType(int code, String desc) {
			this.code = code;
			this.desc = desc;
		}

		public int getCode() {
			return this.code;
		}

		public String getDesc() {
			return this.desc;
		}
	}

	enum CustStatus {
		DISABLE(0, "停用"),
		ENABLE(1, "正常"),
		LOCK(2, "锁定"),
		CANCEL(3, "注销"),
		OPEN_PHONE_ACK_PASSWORD(4, "使用开户手机号登录等待确认交易密码");

		private final Integer code;
		private final String desc;

		private CustStatus(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

		public Integer getCode() {
			return this.code;
		}

		public String getDesc() {
			return this.desc;
		}
	}

	enum CustType {
		VISITOR(0, "游客"),
		GENERAL(1, "普通个户"),
		ADVISOR(2, "认证投顾"),
		OFFICIAL(3, "官方账号"),
		AUTHOR(4, "公司授权人"),
		ESOP(5, "存量ESOP客户");

		private final int code;
		private final String desc;

		private CustType(int code, String desc) {
			this.code = code;
			this.desc = desc;
		}

		public int getCode() {
			return this.code;
		}

		public String getDesc() {
			return this.desc;
		}
	}
}
