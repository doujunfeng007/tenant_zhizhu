package com.minigod.zero.biz.common.constant;

import java.util.ArrayList;
import java.util.List;

public class SunlineCommonConstant {


	//来源安全的短信验证码key
	public static final String KEY_SECURITY_CAPTCHA = "securityCaptcha_";


	public static final String FINANCING_CEILING_MAX = "financingCeilingMax";

	public static final String IPO_VIP_DISCOUNT = "ipoVipDiscount";

	// Ipo预约队列缓存前缀
	public static final String PRE_IPO_QUEUE = "preIpoQueue_";

	// Ipo预约配置缓存前缀
	public static final String PRE_IPO_CONFIG = "preIpoConfig_";

	public static final String INNER_CLIENT_SIDE_SID = "0232d1566c0638a0a7583c967254c717";

	public static final int IS_QUEUE_STS = 1;

	public static final double DEPOSIT_RATE_10 = 0.90;

	public static final double DEPOSIT_RATE_20 = 0.95;

	public static final int DEPOSIT_5 = 5;

	public static final int DEPOSIT_10 = 10;

	public static final int DEPOSIT_20 = 20;

	/** 常量 0 */
	public static final String ZERO = "0";

	/**
	 * 恒生柜台币种类别
	 */
	public enum HsMoneyTypeEnum {
		CNY("0","人民币"),
		USD("1","美元"),
		HKD("2","港币")
		;
		public String value;
		public String description;

		HsMoneyTypeEnum(String value,String description) {
			this.value = value;
			this.description = description;
		}
	}

	public enum WhiteListFunType {
		FUN_ALL("FUN_ALL","全局功能ID"),
		FUN_01("FUN_01","IPO融资认购排队"),
		FUN_02("FUN_02","条件单"),
		FUN_03("FUN_03","智珠VIP"),
		FUN_04("FUN_04","中华通"),
		FUN_05("FUN_05","条件单-专业版"),
		FUN_06("FUN_06","智珠宝基金"),
		FUN_07("FUN_07","智能投顾评论"),
		FUN_08("FUN_08","智能客服"),
		FUN_09("FUN_09","孖展"),
		FUN_10("FUN_10","线上更改客户资料"),
		FUN_11("FUN_11","孖展1.2"),
		FUN_12("FUN_12","孖展1.3")
		;
		public String value;
		public String description;

		WhiteListFunType(String value,String description) {
			this.value = value;
			this.description = description;
		}
		public static List<String> findWhiteKeys(){
			List<String> list = new ArrayList<>();
			for(WhiteListFunType whiteEnum : WhiteListFunType.values()) {
				list.add(whiteEnum.value);
			}
			return list;
		}
	}

	public enum WhiteListConfigStatusType {
		STS_1(1,"全局使用"),
		STS_2(2,"白名单使用"),
		STS_3(3,"停用"),
		;
		public int value;
		public String description;

		WhiteListConfigStatusType(int value,String description) {
			this.value = value;
			this.description = description;
		}
	}

	public enum SubRewardTypeEnum {
		TYP_1(1,"融资打新手续费"),
		TYP_2(2,"现金打新手续费"),
		TYP_3(3,"0本金打新券")
		;
		public int value;
		public String description;

		SubRewardTypeEnum(int value,String description) {
			this.value = value;
			this.description = description;
		}
	}


	public enum UserVipMoneyRecordType {
		TYP_1(1,"佣金"),
		TYP_2(2,"打新"),
		TYP_3(3,"行情"),
		TYP_4(4,"其他")
		;
		public int value;
		public String description;

		UserVipMoneyRecordType(int value,String description) {
			this.value = value;
			this.description = description;
		}
	}

	public enum UserVipMoneyRecordOther {
		RECORD_1("工具服务","形态猎手")
		;
		public String title;
		public String remark;

		UserVipMoneyRecordOther(String title,String remark) {
			this.title = title;
			this.remark = remark;
		}
	}

	/**
	 * Ipo 预约融资认购新股队列状态表
	 */
	public enum IpoPredictFinanceQueueStatus {
		STS_0(0,"未开始"),
		STS_1(1,"可预约"),
		STS_2(2,"预约成功"),
		STS_3(3,"预约失败"),
		STS_4(4,"预约撤回"),
		STS_5(5,"已经提交排队"),
		STS_6(6,"预约结束")
		;
		public int value;
		public String description;

		IpoPredictFinanceQueueStatus(int value,String description) {
			this.value = value;
			this.description = description;
		}
	}

	public enum IpoQueueOrderType {
		TYP_1(1,"普通认购"),
		TYP_2(2,"VIP认购"),
		TYP_3(3,"0本金认购"),
		;
		public int value;
		public String description;

		IpoQueueOrderType(int value,String description) {
			this.value = value;
			this.description = description;
		}
	}

	public enum GeneralQuestionType {
		RISK(1,"风险测评"),

		;
		public int value;
		public String description;

		GeneralQuestionType(int value,String description) {
			this.value = value;
			this.description = description;
		}
	}

	public enum GeneralQuestionCode {
		AGE("age","开户年龄"),
		INVEST_EXPERIENCE("invest_experience","投资经历"),
		INVEST_OBJECTIVE("invest_objective","投资目标"),
		;
		public String value;
		public String description;

		GeneralQuestionCode(String value,String description) {
			this.value = value;
			this.description = description;
		}
	}

	/**
	 * 错误代码分模块定义
	 * */
	public enum ErrorCodeModule {
		GENERAL("general","公用模块"),
		TRADE("trade","交易模块"),
		USER("user","用户模块"),
		MKTINFO("mktinfo","用户模块"),
		WEB_APP("webapp","用户模块"),
		;
		public String code;
		public String description;

		ErrorCodeModule(String code,String description) {
			this.code = code;
			this.description = description;
		}
	}

	public static String getNickName(Integer userId) {
		if(null == userId) return "";
		String userIdStr = userId.toString();
		if(userIdStr.length() <= 6) {
			return "智珠"+userIdStr;
		} else {
			return "智珠"+userIdStr.substring(userIdStr.length()-6,userIdStr.length());
		}
	}

	/**
	 * 风险水平相关
	 * @ClassName: RiskLevel
	 * @Description: 风险水平相关
	 * @author yanghu.luo
	 * @date 2022年8月12日
	 */
	public class RiskLevel {

		/** 风险水平  安全 */
		public static final String A = "A";

		/** 风险水平  警示 */
		public static final String B = "B";

		/** 风险水平  危险 */
		public static final String C = "C";

		/** 风险水平  平仓 */
		public static final String D = "D";

		/** 风险水平 sys_config 模块 */
		public static final String RISK_LEVEL = "riskLevel";

		/** 风险水平 sys_config 警示值key */
		public static final String WARNING = "warning";

		/** 风险水平 sys_config 追收值key */
		public static final String COLLECT = "collect";

		/** 风险水平 sys_config 平仓值key */
		public static final String CLOSE = "close";
	}

}
