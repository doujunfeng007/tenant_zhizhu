package com.minigod.zero.customer.enums;

import com.minigod.zero.core.tool.utils.StringUtil;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/5/7 13:50
 * @description：
 */
public interface FundTradingEnum {

	enum TradingType {

		buy(1, "买"),
		sell(1, "卖"),
        ;

        private final int code;
		private final String desc;

        TradingType(int code, String desc) {
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

	enum TradingStatusType {
		PENDING(100, "待付款"),
		SUBMITTED(200, "已提交"),
		AUTHORIZED(211, "以授权"),
		POOLED(221, "已发送"),
		CONFIRMED(270, "已确定"),
		SETTLED(300, "已清算"),
		REJECTED(400, "拒绝"),
		CANCELED(500, "已取消"),
		FAILED(600, "失败"),
		SUCCESS(700, "成功");

		private final int code;
		private final String desc;

		private TradingStatusType(int code, String desc) {
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
