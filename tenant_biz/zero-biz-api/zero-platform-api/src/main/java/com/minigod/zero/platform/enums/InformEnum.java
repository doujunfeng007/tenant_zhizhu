package com.minigod.zero.platform.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: sunline
 * @Description:
 * @Date: Created in 11:29 2017/10/16
 * @Modified By:
 */
public class InformEnum {

	public static String WECHAT_TOKEN_KEY = "wechat_token_key_jfgj";
	public static String WECHAT_TOKEN_KEY_JLR = "wechat_token_key_jlr";
	public static int WECHAT_TOKEN_TIME = 5400;

	static final Map<Integer,String> wechattempmap = new HashMap<Integer,String>();
	static final Map<Integer,String> wechattempmap2 = new HashMap<Integer,String>();
	public enum WechatTempEnum {
		//智珠环球公众号模板
		GETCASH_SUCCESS(1017 , "提取资金-提出资金成功","AL1gR4KQLPVgHyQ4cQcTMDDPsumWyTwO-VbUoX71vJk","p6jnvI1-c4ZFvKlzVg_7xvR7svzDcoD-ybt-Z7eB4-Q"),//TODO
		INTCASH_SUCCESS(1020 , "存入资金-存入资金成功","AL1gR4KQLPVgHyQ4cQcTMDDPsumWyTwO-VbUoX71vJk","p6jnvI1-c4ZFvKlzVg_7xvR7svzDcoD-ybt-Z7eB4-Q"),//TODO
		SECACCOUNT_SUCCESS(1005 , "存入资金-子账户申请成功","Y1LdCJi0S4Iwk0cofKAREDx4HimFX_bcmw701-D_CBw","aHNhnFTvDO7WbmSMt_uiVoOkJwTDhVpnDyMqf9MPDzQ"),//TODO
		INTCASH_ACCEPT(1021 , "存入资金-存入资金发起","2l2xuIk_SpXu2seK47UFu6ul7Mr_Zfqu7KWtHaRiMqE","NggIMpoz39Fg5DisNjuE50iJ609pHtd4BuFtyb0y_l0"),//TODO
		OPEN_DERI_SUCCESS(1035 , "账户-衍生品申请成功","Y1LdCJi0S4Iwk0cofKAREDx4HimFX_bcmw701-D_CBw","aHNhnFTvDO7WbmSMt_uiVoOkJwTDhVpnDyMqf9MPDzQ"),//TODO
		IPO_APPLY_SUCCESS(1044 , "新股认购-提交申购","LivoaPitIDi_QC6rP3N7w-PDZ4OFcVUfuxMShSIUdO0","ddiWOLpu5IAY-KV6k-Qe7iaRsj8cr5OnKc_Mw9zPsYQ"),//TODO
		IPO_ZQ_SUCCESS(1045 , "新股认购-中签","at7OZ48USmEnwxYJcjjs8U-_GbsoI8Rs51xyedKVSfc","_lTbllMA0nafdgf7T2cGhbFkfalmjcVdHnW_y7HnglQ"),//TODO
		IPO_WZQ_SUCCESS(1046 , "新股认购-未中签","qir8vdKrq2Ee7eC8OlXVOd3_7oL0WG2lSJ91Jz0xs4A","8-ntpLdmqVH0Kh_Oi49LreVaCXj5jVNlrVzzT7xZ4oY"),//TODO
		CHANGE_UPDOWN_INFORM(1097 , "股价提醒-按日涨/跌幅","EzsMuKPyY1HcocJ4EzPqArCA_Uif5LnFWPoJLTuI_lQ","FGcRvl0QAR9VW06yNzXwY_XpRQi1-lfIZIZF6DcP8rM"),//TODO
		PRICE_UPDOWN_INFORM(1098 , "股价提醒-按目标价","HsGlTDXtnZ-93YcYxDNs65smKAk1rxeg0lJaSyc3Xhs","0zgS-7YOHxdXNN-Y6etnjohVmhlAefvREvJbYJI1W40"),
		//        TRADE_INFORM_SUCCESS(1099 , "交易提醒-交易成功","KhKKsUMLYnVCpqhhbzOAOlDz4ParXHmuZjAesYn8jrY"),//TODO
		LEVEL2_EXPIRE(1015 , "港股level2行情到期","HT6hfu156VKyoELglj7a5-FQa8-ehAuOLQDSm_A47sQ","qkChNkMPYpP9CBRPjF4O8pp005W7wYFAfKcpVsd6KXQ"),//TODO
		LEVEL2_US_EXPIRE(1055 , "美股Nasdaq最优报价行情到期","HT6hfu156VKyoELglj7a5-FQa8-ehAuOLQDSm_A47sQ","qkChNkMPYpP9CBRPjF4O8pp005W7wYFAfKcpVsd6KXQ"),//TODO
		HAS_LOAD_EXPIRE(1056 , "免佣到期(入金用户)","HT6hfu156VKyoELglj7a5-FQa8-ehAuOLQDSm_A47sQ","qkChNkMPYpP9CBRPjF4O8pp005W7wYFAfKcpVsd6KXQ"),//TODO
		TRAN_STOCK_OUT_SUCCESS(1089 , "转出股票-转出成功","JBvJmMXC8xQTpVV3qxEHP5_Tk1ihm0IY5faB7gyrDDM","ErS9QxUAesqZAF5RP7HSjuDLZNIxrHtnq3c3R0rFBtY"),//TODO
		TRAN_STOCK_IN_SUCCESS(1090 , "转入股票-转入成功","JBvJmMXC8xQTpVV3qxEHP5_Tk1ihm0IY5faB7gyrDDM","ErS9QxUAesqZAF5RP7HSjuDLZNIxrHtnq3c3R0rFBtY"),//TODO
		IPO_CAN_APPLY(1091 , "新股可申购" , "hLc-ydTSIfJ7XXbDsfqv38N1FJtKclfHqdcQYlU7gpQ" , "UG1_tmi0D7_XnlYVvPuSwAHQc9UAryVnA_eLc6O4T-4"),
		IPO_GUESS_APPLY_OR_SELL(1092 , "牛人打新认购或卖出提醒" , "zAJQ8JSKLMYIuKFyzOTMefUXQcoChbU2c_iBcDVHgJw" , "FLRUx-jVPADmr9uI6U6arrUhvZwDPRqet-vJvaAyhJA"),
		STRATEGY_INFORM(1095 , "条件单提醒" , "d3pkwp82igmpt0iWpWv-9gR04V0GWYRKtlKQlVaAacg" , "sq4D0Rvp3se-vXf3LYD6qzu67y5OmdcrS11amezU0y0"),
		VIP_SUCCESS(1096 , "VIP开通提醒" , "43fdJGTXYv7IphszHNkgSVkJQOg2Tl0Q7PZy20Ljx4c" , "xg8XQjCg76c_ToOkHzd2oeVkPcpIf1sGmO6Tyn4xdU0"),

		//        OPERATION_MONITORING(1100 , "运营监控通知" , "_xkrK3sbKmJx7zQ6QVBIU0mvalyXc0oF_wC9T_x_daU" , "uHg06UjwF--D4vucJjohvblGb2kzGvuPZ5h7M3na0wg"),
		OPERATION_MONITORING(1100 , "运营监控通知" , "rrBKMrWyARe6EYX62ex7AmXZ9bQQHq8FqKgXCz_vASE" , "uHg06UjwF--D4vucJjohvblGb2kzGvuPZ5h7M3na0wg"),

		//掌上智珠经理人公众号模板
		BROKER_INVEST_ARTICLE(1093 , "经理人投资内参新文章发布" , "hLc-ydTSIfJ7XXbDsfqv38N1FJtKclfHqdcQYlU7gpQ" , "06Oyns3ffUXlr18f6c8sUYn86YuMXSF17ujHrSvhqU0"),
		BROKER_INTCASH_SUCCESS(1094 , "经理人存入资金-存入资金成功" , "ZeK92bvMhIBgI_c1gdtmVevcwx_GBQCtvbSNf5kY2zc" , "dIvB-aweNJbpDUry4oIlacwlXQiaarKP4QJHMj6w9jM"),

		;
		private int code;
		WechatTempEnum(int code, String desc, String testid, String formalid) {
			this.code = code;
			wechattempmap.put(code,testid);
			wechattempmap2.put(code, formalid);
		}
		public int getCode () { return this.code; }
		public static final String getWechatTempId(WechatTempEnum temp,boolean istest) {
			if(istest){
				return wechattempmap.get(temp.getCode());
			}else{
				return wechattempmap2.get(temp.getCode());
			}
		}
	}

	public static final Map<Integer,List<Integer>> wechatgroup = new HashMap<Integer,List<Integer>>();
	public enum WechatGroupEnum {
		GROUP101(101,new Integer[]{ 1021},"出入金受理提醒"),
		GROUP102(102,new Integer[]{ 1017,1018},"出入金成功提醒"),
		GROUP103(103,new Integer[]{ },"转仓受理提醒"),
		GROUP104(104,new Integer[]{ 1089,1090},"转仓成功提醒"),
		GROUP105(105,new Integer[]{ },"Level2行情生效提醒"),
		GROUP106(106,new Integer[]{ 1015,1055},"Level2行情到期提醒"),
		GROUP107(107,new Integer[]{ },"免佣生效提醒"),
		GROUP108(108,new Integer[]{ 1056},"免佣到期提醒"),
		GROUP109(109,new Integer[]{ 1091},"新股可申购提醒"),
		GROUP110(110,new Integer[]{ 1044},"新股申购成功提醒"),
		GROUP111(111,new Integer[]{ 1045,1046},"新股中签提醒"),
		GROUP112(112,new Integer[]{ 1097,1098},"股价提醒"),
		GROUP113(113,new Integer[]{ 1092},"打新牛人提醒"),
		GROUP114(114,new Integer[]{ 1093},"经理人投资内参新文章发布"),
		GROUP115(115,new Integer[]{ 1094},"经理人存入资金-存入资金成功"),
		GROUP116(116,new Integer[]{ 1095},"条件单提醒"),
		;
		WechatGroupEnum(int code, Integer[] groups, String desc) {
			List<Integer> list = new ArrayList<Integer>();
			for(Integer group:groups){
				list.add(group);
			}
			wechatgroup.put(code,list);
		}
		public static final Integer getWechatGroupId(int gcode) {
			for (Map.Entry<Integer, List<Integer>> entry : wechatgroup.entrySet()) {
				List<Integer> lvt = entry.getValue();
				if(lvt.contains(gcode)){
					return entry.getKey();
				}
			}
			return null;
		}
	}

	public enum EmailTempEnum {
		TQZJ_KF_SUCCESS(1056 , "提出资金成功(客服)"),
		CRZJ_KF_SUCCESS(1057 , "存入资金成功(客服)"),
		TQZJ_YH_SUCCESS(1061 , "提出资金成功(用户)"),
		CRZJ_YH_SUCCESS(1060 , "存入资金成功(用户)"),
		ZC_ZR_SUCCESS(1062 , "转入成功"),
		ZC_ZC_SUCCESS(1063 , "转出成功"),
		XGJYMM_SUCCESS(1064 , "修改交易密码"),
		JYJS_SUCCESS(1065 , "交易解锁"),
		ZHDJ_SUCCESS(1066 , "帐户冻结"),
		CZJYMM_SUCCESS(1067 , "重置交易密码"),
		XD_SUCCESS(1068 , "下单成功"),
		GD_SUCCESS(1069 , "改单成功"),
		CD_SUCCESS(1070 , "撤单成功"),

		TRADING_AMOUNT_LIMIT(1071 , "客户单只股票交易额超限"),
		TRADING_PRICE_LIMIT(1072 , "当日成交价超限"),
		YESTERDAY_PRICE_LIMIT(1073 , "下单价格对比上日收市价超限"),
		TRADING_AMOUNT_PRICE_LIMIT(1074 , "客户单只股票交易额&价格波动超限"),
		ISSUE_PRICE_LIMIT(1075 , "新股首日成交价超限"),
		MODIFY_ORDER_LIMIT(1076 , "改单次数超限"),
		CANCEL_ORDER_LIMIT(1077 , "撤单次数超限"),
		IPO_APPLY_SUCCESS(1086 , "新股申购成功"),
		IPO_ZQ_SUCCESS(1087 , "新股中签"),
		IPO_WZQ_SUCCESS(1088 , "新股未中签"),
		IPO_REC_APPLY(2028 , "新股推荐审核"),
		IPO_SIGNAL_REVIEW(2029 , "新股变盘审核"),
		XJHG_SUCESS(2040 , "现金回购"),
		GGQ_SUCESS(2041 , "供股权"),
		YGDX_SUCESS(2042 , "以股代息"),
		ENTERPRISE_RECOGNIZE(2044, "企业用户认证"),
		ENTERPRISE_APPLY_FAIL(2045, "企业审批未通过"),
		ENTERPRISE_APPLY_SUCCESS(2056, "企业审批通过"),
		ORDER_REJECT(2064 , "废单"),
		IPOQUEUE_FAIL(2065 , "未抢到额度邮箱提醒"),

		INVITE_ACTIVITY_UPGRADE_MSG(2211,"官网邀请_邀请人升级"),
		;
		private int code;
		private String typeValue;

		EmailTempEnum(int code, String typeValue) {
			this.code = code;
			this.typeValue = typeValue;
		}

		public int getCode() {
			return code;
		}

		public String getTypeValue() {
			return typeValue;
		}
	}

	public enum MsgTempEnum {
		HK_L2_PUSH(1091 , "港股level2行情发放"),
		US_L2_PUSH(1092 , "美股level2行情发放"),
		IPO_APPLY_SUCCESS(1083 , "新股申购成功"),
		IPO_ZQ_SUCCESS(1084 , "新股中签"),
		IPO_WZQ_SUCCESS(1085 , "新股未中签"),
		HNEW_SELL_PUSH(1078 , "打新卖出提醒"),
		HNEW_BUY_PUSH(1079 , "打新认购提醒"),
		OUT_SUCCESS(1019 , "转出成功(消息通知)"),
		SHIFT_XT_SUCCESS(1090 , "转入成功(消息通知)"),
		TRADE_PWD_EXPIRED(2001 , "交易密码过期提醒"),
		HNEW_SIGNAL_SUCCESS(2023 , "新股变盘提醒"),
		HNEW_SIGNAL_CHIYOU(2030 , "新股持有提醒"),
		STRATEGY_INFORM(2039,"条件单提醒"),
		STRATEGY_ORDER_INVALID(2058 , "条件单股价失效提醒"),
		IPO_QUEUE_SUCCESS(2061 ,"IPO排队认购成功"),
		RISK_EXPIRED(2062 ,"风险测评过期"),
		VIP_OVERDUE_MSG(2066 , "VIP会员到期提醒"),
		FORM_HUNTER_NOT_PURCHASED_MSG(2086 , "形态猎手未购买提醒"),
		FORM_HUNTER_OVERDUE_MSG(2093 , "形态猎手过期提醒"),
		FORM_HUNTER_PAY_SUCCESS_MSG(2087 , "形态猎手支付成功提醒"),
		FORM_HUNTER_PAY_FAIL_MSG(2088 , "形态猎手支付失败提醒"),
		FORM_HUNTER_WILL_OVERDUE_MSG(2089 , "形态猎手将到期提醒"),
		FORM_HUNTER_FOLLOW_NEW_MSG(2090 , "形态猎手将到期提醒"),
		FORM_HUNTER_START_SIGN_MSG(2091,"形态猎手信号产生"),
		FORM_HUNTER_END_SIGN_MSG(2092,"形态猎手信号结束"),

		INVITE_ACTIVITY_REGISTER_MSG(2212,"官网邀请_好友注册通知"),
		INVITE_ACTIVITY_OPEN_MSG(2213,"官网邀请_好友开户通知"),
		INVITE_ACTIVITY_DEPOSIT_MSG(2214,"官网邀请_好友入金通知"),
		INVITE_ACTIVITY_UPGRADE_MSG(2215,"官网邀请_邀请人升级通知"),
		INVITE_ACTIVITY_REMIND_MSG(2216,"官网邀请_礼包到期通知"),
		;
		private int code;
		private String typeValue;
		MsgTempEnum(int code, String typeValue) {
			this.code = code;
			this.typeValue = typeValue;
		}

		public int getCode() {
			return code;
		}

		public String getTypeValue() {
			return typeValue;
		}
	}

	// 模板类型业务类型
	public enum BusTypeEnum {
		MAIL(1,"邮件通知"),
		MOBILE(2,"短信通知"),
		SYS(3,"系统通知"),
		MSG(4,"消息通知");

		private Integer typeCode;
		private String typeValue;

		BusTypeEnum(Integer typeCode, String typeValue) {
			this.typeCode = typeCode;
			this.typeValue = typeValue;
		}

		public Integer getTypeCode () { return this.typeCode; }

		public static Integer getTypeCode (Integer index) {
			for (BusTypeEnum typeEnum : BusTypeEnum.values()) {
				if (typeEnum.getTypeCode().equals(index)) {
					return typeEnum.typeCode;
				}
			}
			return null;
		}

		public static String getTypeValue(Integer index) {
			for (BusTypeEnum typeEnum : BusTypeEnum.values()) {
				if (typeEnum.getTypeCode().equals(index)) {
					return typeEnum.typeValue;
				}
			}
			return null;
		}
	}

	// 模板类型中发送方式
	public enum SendWayEnum {
		SELF_MOTION(0,"自动"),
		MANUAL_OPERATION(1,"手动");

		private Integer typeCode;
		private String typeValue;

		SendWayEnum(Integer typeCode, String typeValue) {
			this.typeCode = typeCode;
			this.typeValue = typeValue;
		}

		public Integer getTypeCode () { return this.getTypeCode(); }

		public static Integer getTypeCode (Integer index) {
			for (SendWayEnum wayEnum : SendWayEnum.values()) {
				if (wayEnum.getTypeCode().equals(index)) {
					return wayEnum.typeCode;
				}
			}
			return null;
		}

		public static String getTypeValue (Integer index) {
			for (SendWayEnum wayEnum : SendWayEnum.values()) {
				if (wayEnum.getTypeCode().equals(index)) {
					return wayEnum.typeValue;
				}
			}
			return null;
		}

	}

	// 系统通知信息 消息类型
	public enum MsgType {
		ACTIVITY("A","活动"),
		REMIND("R" , "提醒"),
		NOTICE("N" , "公告"),
		IMPORTANT_NEWS("X" , "要闻"),
		BROADCAST("B" , "播报");

		private String typeCode;
		private String typeValue;

		MsgType(String typeCode, String typeValue) {
			this.typeCode = typeCode;
			this.typeValue = typeValue;
		}

		public String getTypeCode() {
			return typeCode;
		}

		public void setTypeCode(String typeCode) {
			this.typeCode = typeCode;
		}

		public String getTypeValue() {
			return typeValue;
		}

		public void setTypeValue(String typeValue) {
			this.typeValue = typeValue;
		}
	}

	// 系统通知信息 消息级别
	public enum MsgLevelEnum {
		IMPORTANCE("I" , "重要"),
		BENERAL("G" , "普通");

		private String typeCode;
		private String typeValue;

		MsgLevelEnum(String typeCode, String typeValue) {
			this.typeCode = typeCode;
			this.typeValue = typeValue;
		}

		public String getTypeCode() {
			return typeCode;
		}

		public void setTypeCode(String typeCode) {
			this.typeCode = typeCode;
		}

		public String getTypeValue() {
			return typeValue;
		}

		public void setTypeValue(String typeValue) {
			this.typeValue = typeValue;
		}
	}

//    // 系统通知信息 客户端类型
//    public enum ClientTypeEnum {
//        ALL(-1 , "全部终端"),
//        ANDROID(1 , "安卓"),
//        IOS(0 , "苹果");
//
//        private Integer typeCode;
//        private String typeValue;
//
//        private ClientTypeEnum (Integer typeCode , String typeValue) {
//            this.typeCode = typeCode;
//            this.typeValue = typeValue;
//        }
//
//        public Integer getTypeCode() {
//            return typeCode;
//        }
//
//        public void setTypeCode(Integer typeCode) {
//            this.typeCode = typeCode;
//        }
//
//        public String getTypeValue() {
//            return typeValue;
//        }
//
//        public void setTypeValue(String typeValue) {
//            this.typeValue = typeValue;
//        }
//    }

	// 系统通知消息 消息分组
	public enum MsgGroupEnum {
		PERSON("P" , "个人"),
		ALL("A" , "全站"),
		LABEL("L" , "标签用户"),
		TEAM("T" , "用户分组");

		private String typeCode;
		private String typeValue;

		MsgGroupEnum(String typeCode, String typeValue) {
			this.typeCode = typeCode;
			this.typeValue = typeValue;
		}

		public String getTypeCode() {
			return typeCode;
		}

		public void setTypeCode(String typeCode) {
			this.typeCode = typeCode;
		}

		public String getTypeValue() {
			return typeValue;
		}

		public void setTypeValue(String typeValue) {
			this.typeValue = typeValue;
		}
	}

	// 短信通知  用户类型
	public enum UserTypeEnum {
		ALL(0,"全部用户"),
		TD_USER(1,"特定用户"),
		IMPORT_USER(2,"导入用户");

		private Integer typeCode;
		private String typeValue;

		UserTypeEnum(Integer typeCode, String typeValue) {
			this.typeCode = typeCode;
			this.typeValue = typeValue;
		}

		public Integer getTypeCode() {
			return typeCode;
		}

		public void setTypeCode(Integer typeCode) {
			this.typeCode = typeCode;
		}

		public String getTypeValue() {
			return typeValue;
		}

		public void setTypeValue(String typeValue) {
			this.typeValue = typeValue;
		}
	}

	// 系统通知信息 推送类型
	public enum SendTypeEnum {
		STRONG(0 , "强消息"),
		WEAK(1 , "弱消息");

		private Integer typeCode;
		private String typeValue;

		SendTypeEnum(Integer typeCode, String typeValue) {
			this.typeCode = typeCode;
			this.typeValue = typeValue;
		}

		public Integer getTypeCode() {
			return typeCode;
		}

		public void setTypeCode(Integer typeCode) {
			this.typeCode = typeCode;
		}

		public String getTypeValue() {
			return typeValue;
		}

		public void setTypeValue(String typeValue) {
			this.typeValue = typeValue;
		}
	}

	public enum SendWayTimeEnum {
		FORTHWITH(0 , "即时"),
		TIMING(1 , "定时");

		private int typeCode;
		private String  typeValue;

		SendWayTimeEnum(int typeCode, String typeValue) {
			this.typeCode = typeCode;
			this.typeValue = typeValue;
		}

		public int getTypeCode() {
			return typeCode;
		}

		public void setTypeCode(int typeCode) {
			this.typeCode = typeCode;
		}

		public String getTypeValue() {
			return typeValue;
		}

		public void setTypeValue(String typeValue) {
			this.typeValue = typeValue;
		}
	}

	// 消息发送通用状态
	public enum SendStatusEnum {
		NO_SEND(0 , "未发送"),
		SUCCESS_SEND(1 , "发送成功"),
		FAIL_SEND(2 , "发送失败"),
		SEND_ING(3 , "发送中");

		private int typeCode;
		private String typeValue;

		SendStatusEnum(int typeCode, String typeValue) {
			this.typeCode = typeCode;
			this.typeValue = typeValue;
		}

		public int getTypeCode() {
			return typeCode;
		}

		public void setTypeCode(int typeCode) {
			this.typeCode = typeCode;
		}

		public String getTypeValue() {
			return typeValue;
		}

		public void setTypeValue(String typeValue) {
			this.typeValue = typeValue;
		}
	}

	public enum CodeToBusEnum {
		REGISTER_SUCCESS(1000 , "注册成功未开户"),
		COMMIT_OPEN_INFO(1001 , "提交开户资料"),
		OPEN_SUCCESS(1002 , "开户成功"),
		OPEN_FAIL(1003 , "开户失败"),
		COMMIT_SECACCOUNT(1004 , "子账户申请发起"),
		SECACCOUNT_SUCCESS(1005 , "子账户申请成功"),
		SHIFT_ACCEPT(1006 , "转入受理"),
		SHIFT_SUCCESS(1007 , "转入成功(短信)"),
		NO_LOAD(1008 , "免佣(通知)"),
		NO_LOAD_EXPIRE(1009 , "免佣到期(无入金客户)"),
		GRANT(1010 , "发放"),
		BIRTHDAY(1011 , "生日"),
		HOLIDAYS(1012 , "节假日"),
		ANNIVERSARY(1013 , "周年纪念日"),
		STOP_BUS(1014 , "休市"),
		LEVEL2_EXPIRE(1015 , "港股level2行情到期"),
		GETCASH_ACCEPT(1016 , "提取资金受理"),
		GETCASH_SUCCESS(1017 , "提出资金成功"),
		OUT_ACCEPT(1018 , "转出受理"),
		OUT_DX_SUCCESS(1089 , "转出成功(短信)"),
		INTCASH_SUCCESS(1020 , "存入资金成功(消息通知)"),
		INTCASH_ACCEPT(1021 , "存入资金发起(消息通知)"),
		PRICE_UP_INFORM(1022 , "上涨提醒"),
		CHANGE_UP_INFORM(1023 , "涨幅提醒"),
		PRICE_DOWN_INFORM(1024 , "下跌提醒"),
		CHANGE_DOWN_INFORM(1025 , "跌幅提醒"),
		ADD_FRIEND_APPLY(1026 , "添加好友"),
		ADD_FRIEND_SUCCESS(1027 , "添加好友成功"),
		FRIEND_REGISTER(1028 , "好友注册"),
		FRIEND_OPEN_ACCOUNT(1029 , "好友开户"),
		HAS_LOAD_EXPIRE(1030 , "免佣到期(入金用户)"),
		US_ACCOUNT_SUCCESS(1031 , "美股开户成功"),
		US_ACCOUNT_FAIL(1032 , "美股开户失败"),
		HC_SUCCESS(1033 , "换汇成功"),
		HC_FAIL(1034 , "换汇失败"),
		OPEN_DERI_SUCCESS(1035 , "衍生品权限开通成功"),
		OPEN_DERI_FAIL(1036 , "衍生品权限开通失败"),
		USER_DOUBLE_VERIFY_SUCCESS(1037, "您刚刚授权{0}为授信设备，此后在此设备登录不需再进行双重身份认证。当连续7日内未在此设备上登录，信任关系将被解除。如以上非您本人操作，请联系客服中心：400-8439-666。"),
		USGRANT(1038 , "美股发放"),
		BROKER_REG_CAPTCHA(1039 , "掌上智珠经理人手机验证码"),
		SEND_CAPTCHA(1040 , "yff证券验证码"),
		SEND_PWD_ERROR_COUNT(1041 , "帐号锁定提示"),
		SEND_BACK_PWD(1042 , "密码设置成功提示"),
		SEND_OLD_PHONE(1043 , "修改手机号"),
		IPO_APPLY_SUCCESS(1044 , "新股认购成功"),
		IPO_ZQ_SUCCESS(1045 , "已中签"),
		IPO_WZQ_SUCCESS(1082 , "未中签"),
		OPEN_OFFLINE_SUCCESS(1046 , "香港预约开户受理"),
		LEVEL2_US_EXPIRE(1055 , "美股level2行情到期"),
		CRZJ_SUCCESS(1058 , "存入资金成功(短信)"),
		TQZJ_SUCCESS(1059 , "提取资金成功(短信)"),
		TRAN_STOCK_OUT_SUCCESS(1089 , "转出成功"),
		TRAN_STOCK_IN_SUCCESS(1090 , "转入成功"),
		SYSTEM_ALARM(2008, "系统告警"),
		MKT_PURCHASE_SUCCESS_ORDER(2010, "行情购买成功-订单通知"),
		IPO_APPLY_FAILED(2031 , "新股认购失败"),
		ACTIVITY_ADD_LOTTERY_NUM(2032 , "双12活动新增抽奖次数"),
		BUSINESS_ACTIVITY_ADD_LOTTERY_NUM(2033 , "商务渠道活动新增抽奖次数"),
		USER_DOUBLE_VERIFY_FIRST_SUCCESS(2043, "您刚刚在{0}进行设备认证，如非您本人操作，请联系客服中心：400-8439-666咨询。"),
		VIP_OVERDUE_SM(2067 , "VIP会员到期提醒-短信"),

		INVITE_ACTIVITY_REGISTER_MSG(2204,"官网邀请_好友注册成功"),
		INVITE_ACTIVITY_OPEN_MSG(2205,"官网邀请_好友开户成功"),
		INVITE_ACTIVITY_DEPOSIT_MSG(2206,"官网邀请_好友入金/转仓成功"),
		INVITE_ACTIVITY_UPGRADE_MSG(2207,"官网邀请_邀请人升级"),
		INVITE_ACTIVITY_EXPIRE_REMIND_MSG(2208,"官网邀请_升级礼到期"),
		INVITE_ACTIVITY_OPEN_REMIND_MSG(2209,"官网邀请_提醒开户"),
		INVITE_ACTIVITY_DEPOSIT_REMIND_MSG(2210,"官网邀请_提醒入金"),
		ZA_SECACCOUNT_SUCCESS(2228 , "众安子账户开通成功"),
		HK_REGISTER(2235, "好友注册通知"),
		HK_LOGIN(2236, "好友注册登陆APP通知"),
		HK_INPUT_GOLD(2237, "好友入金达标通知"),
		HK_REMIND_DOWNLOAD_LOGIN(2238, "提醒下载登录APP"),
		HK_REMINDER_DEPOSIT(2239, "提醒入金"),

		MKT_PURCHASE_SUCCESS_US_L1_QUOTES(3039, "行情权限生效-美股level1行情发放"),
		MKT_PURCHASE_SUCCESS_HK_L2_QUOTES(3040, "行情权限生效-港股level2行情发放"),
		MKT_US_L1_QUOTES_USED(3041, "行情权限生效-美股level1行情到期"),
		MKT_L2_QUOTES_USED(3042, "行情权限生效-港股level2行情到期"),
		;

		private Integer typeCode;
		private String typeValue;

		CodeToBusEnum(Integer typeCode, String typeValue) {
			this.typeCode = typeCode;
			this.typeValue = typeValue;
		}

		public Integer getTypeCode() {
			return typeCode;
		}

		public void setTypeCode(Integer typeCode) {
			this.typeCode = typeCode;
		}

		public String getTypeValue() {
			return typeValue;
		}

		public void setTypeValue(String typeValue) {
			this.typeValue = typeValue;
		}

	}

	public static void main(String[] args) {
		Integer typeCode = BusTypeEnum.getTypeCode(1);
		System.out.println(typeCode);

		System.out.println(BusTypeEnum.getTypeValue(2));

		System.out.println(MsgType.ACTIVITY.getTypeCode());
	}
}
