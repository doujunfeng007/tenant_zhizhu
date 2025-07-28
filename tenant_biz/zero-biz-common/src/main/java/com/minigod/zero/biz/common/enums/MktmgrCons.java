package com.minigod.zero.biz.common.enums;



public interface MktmgrCons {

	public static enum CodeType{
		MKTMGR_TIME_ERROR(887, "不在交易时间段"),
	 	MKTMGR_CLICK_ERROR(888, "点击数不够或无权限"),
    	MKTMGR_STREAM_ERROR(889, "服务已过期"),
	 	MKTMGR_SESSION_ERROR(890, "行情认证失败"),
	 	MKTMGR_APPLY_ERROR(891, "行情申请失败"),
	 	MKTMGR_HK_ERROR(892, "当前行情套餐尚未到期"),
	 	MKTMGR_US0_ERROR(893, "操作失败，存在未进行美股投资者身份认证的用户"),
	 	MKTMGR_US1_ERROR(894, "操作失败，不能为美股非专业投资者赠送专业版美股行情套餐"),
	 	MKTMGR_US2_ERROR(895, "操作失败，只能为美股专业投资者赠送专业版美股行情套餐"),
	 	HK_QUOT_EXPIRED_L2_HANS(896, "港股L2行情已到期"),
	 	HK_QUOT_EXPIRED_L2_HANT(897, "港股L2行情已到期"),
	 	HK_QUOT_EXPIRED_L2_EN(898, "The Hong Kong stock market L2 market has expired"),
	 	US_QUOT_EXPIRED_L2_HANS(899, "美股L2行情已到期"),
		US_QUOT_EXPIRED_L2_HANT(900, "美股L2行情已到期"),
		US_QUOT_EXPIRED_L2_EN(901, "The US stock market L2 market has expired"),
		A_QUOT_EXPIRED_L2_HANS(902, "a股L2行情已到期"),
		A_QUOT_EXPIRED_L2_HANT(903, "a股L2行情已到期"),
		A_QUOT_EXPIRED_L2_EN(904, "The a stock market L2 market has expired"),
		HK_QUOT_EXPIRED_L1_HANS(905, "港股L1行情已到期"),
		HK_QUOT_EXPIRED_L1_HANT(906, "港股L1行情已到期"),
		HK_QUOT_EXPIRED_L1_EN(907, "The Hong Kong stock market L1 market has expired"),
		US_QUOT_EXPIRED_L1_HANS(908, "美股L1行情已到期"),
		US_QUOT_EXPIRED_L1_HANT(909, "美股L1行情已到期"),
		US_QUOT_EXPIRED_L1_EN(910, "The US stock market L1 market has expired"),
		A_QUOT_EXPIRED_L1_HANS(911, "a股L1行情已到期"),
		A_QUOT_EXPIRED_L1_HANT(912, "a股L1行情已到期"),
		A_QUOT_EXPIRED_L1_EN(913, "The a stock market L1 market has expired"),
	 	QUOT_LOGIN_ELIMINATE_HANS(914, "高级行情已被其他设备占用，是否重新启用"),
	 	QUOT_LOGIN_ELIMINATE_HANT(915, "高級行情已被其他設備佔用，是否重新啟用"),
	 	QUOT_LOGIN_ELIMINATE_EN(916, "Advanced quotes has been used by another device. Restart quote service?"),
	 	;
        private int code;
        private String message;

        private CodeType(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

	}

	public static enum OrderWay {
		_ZG("1", "支付"), _ZS("2", "赠送");
		private String val;

		private OrderWay(String val, String desc) {
			this.val = val;
		}

		public String val() {
			return val;
		}
	}


	public static enum PCode {
		_TC1("1", "套餐1(港串流)"), _TC2("2", "套餐2(bpm&点击报价)"), _TC4("4", "套餐4(美串流)");
		private String val;

		private PCode(String val, String desc) {
			this.val = val;
		}

		public String val() {
			return val;
		}

		public static Boolean isExist(String flag) {
			boolean bool = false;
			for (PCode ext : PCode.values()) {
				if (ext.val().equals(flag)) {
					bool = true;
				}
			}
			return bool;
		}

	}

	public static enum MarketType {
		_GGHQ("1", "港股行情","HK"), _MGHQ("2", "美股行情","US");
		private String val;
		private String key;
		private MarketType(String val, String desc,String key) {
			this.val = val;
			this.key = key;
		}

		public String key() {
			return key;
		}


		public String val() {
			return val;
		}
	}

	public static enum FeeWay {
		_CL("1", "串流"), _BMP("2", "bmp"),_DJ("3", "点击");
		private String val;

		private FeeWay(String val, String desc) {
			this.val = val;
		}

		public String val() {
			return val;
		}
	}


	public static enum Exchange{
		A("NYSE"),B("AMEX/ARCA"),C("Nasdaq");
		private String desc;
		private Exchange(String desc) {
			this.desc = desc;
		}
		public String desc(){
			return desc;
		}

	}

}
