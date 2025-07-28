package com.minigod.zero.biz.common.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @program: yff-mktmgr
 * @description: StockCommon
 * @author: BPM_support2
 * @create: 2021-12-23 16:32
 **/

public class StockCommon {

	// a股兜底L1
	public static final Long DEFAULT_A_L1_ID = 17L;


    /**
     * 收费模式
     */
    public enum FeeTypeEnum {
        STREAM(0,"串流服务"), CLICK_QUOTE(1,"点击报价"),
        TIME_FREE(2,"时长收费"),CHARGE_FREE(3, "包干免费"),
        FREE(4, "免费服务"),NO_SHOW(5,"非展示");

        public static final Integer STREAM_VALUE = 0;
		public static final Integer CLICK_QUOTE_VALUE = 1;
		public static final Integer TIME_FREE_VALUE = 2;
		public static final Integer CHARGE_FREE_VALUE = 3;
		public static final Integer FREE_VALUE = 4;
		public static final Integer NO_SHOW_VALUE = 5;

        private final Integer code;
        private final String info;

        FeeTypeEnum(Integer code, String info) {
            this.code = code;
            this.info = info;
        }

        public Integer getCode() {
            return code;
        }

        public String getInfo() {
            return info;
        }
    }
    /**
     * 证券市场
     */

    public enum MarketCodeEnum {
        SH("sh", "上交所"), SZ("sz", "深交所"),
        HK("hk","港交所"), NY("ny","纽交所NYSE"),
        OQ("oq","纳斯达克交易所"),AM("am","美交所AMEX"),
        AR("ar","增长板市场ARCA");

        private final String code;
        private final String info;

        MarketCodeEnum(String code, String info) {
            this.code = code;
            this.info = info;
        }

        public String getCode() {
            return code;
        }

        public String getInfo() {
            return info;
        }
    }


    /**
     * 股票证券市场
     */

    public enum StockMarketTypeEnum {
        A("A", "A股"), HK("HK", "港股"),
        US("US","美股");

        private final String code;
        private final String info;

        StockMarketTypeEnum(String code, String info) {
            this.code = code;
            this.info = info;
        }

        public String getCode() {
            return code;
        }

        public String getInfo() {
            return info;
        }
    }

    /**
     * 品种股票市场
     */
    public enum QuoteMarketTypeEnum {
        A("0", "A股"), HK("1", "港股"),
        US("2","美股"),OTHER("3","其他");

        private final String code;
        private final String info;

        QuoteMarketTypeEnum(String code, String info) {
            this.code = code;
            this.info = info;
        }

        public String getCode() {
            return code;
        }

        public String getInfo() {
            return info;
        }
    }

    /**
     * 导出时是否下载明细
     */

    public enum DownDetailTypeEnum {
        YES(1, "是"), NO(0, "否");


        private final int code;
        private final String info;

        DownDetailTypeEnum(int code, String info) {
            this.code = code;
            this.info = info;
        }

        public int getCode() {
            return code;
        }

        public String getInfo() {
            return info;
        }
    }

    /**
     * 是否是大陆
     */
    public enum MainlandEnum {
        YES(1, "是大陆"), NO(0, "非大陆");


        private final int code;
        private final String info;

        MainlandEnum(int code, String info) {
            this.code = code;
            this.info = info;
        }

        public int getCode() {
            return code;
        }

        public String getInfo() {
            return info;
        }
    }


    /**
     * 行情品种
     */
    public enum QuotesTypeEnum {
        HK_STATIC("HK_Static", "静态数据"),
        HK_DELAY("HK_Delay", "延迟行情"),
        HK_BMP("HK_BMP", "BMP"),
        HK_LV_2_STREAM("HK_LV2_Stream", "LV2"),
        HK_LV1_HK_MOB("HK_LV1_HK_MOB", "LV1-hk-Mobile"),
        HK_QUOTE_1("HK_Quote1", "Quote1"),
        HK_QUOTE_2("HK_Quote2", "Quote2"),
        HK_CAPPED("HK_Capped", "CAPPED"),
        HK_INDEX_STATIC("HK_Index_Static", "静态数据"),
        HK_INDEX_DELAY("HK_Index_Delay", "延迟行情"),
        HK_INDEX_BMP("HK_Index_BMP", "BMP"),
        HK_INDEX_STREAM("HK_Index_Stream", "实时数据"),
        NASDAQ_STATIC("Nasdaq_Static", "静态数据"),
        NASDAQ_DELAY("Nasdaq_Delay", "延迟数据"),
        NASDAQ_NLS("Nasdaq_NLS", "NLS"),
        NASDAQ_BASIC_LV_1("Nasdaq_Basic_LV1", "Basic_lv1"),
		NASDAQ_BASIC_LV_1_PRO("Nasdaq_Basic_LV1_Pro", "Basic_lv1_pro"),
        A_STATIC("A_Static", "静态数据"),
        A_DELAY("A_Delay", "延迟数据"),
        A_LV_1("A_LV1", "LV1"),
        A_SH_LV1("A_SH_LV1", "A_SH_LV1"),
        A_SZ_LV1("A_SZ_LV1", "A_SZ_LV1"),
        A_SH_QUOTE("A_SH_QUOTE", "A_SH_QUOTE"),
        A_SZ_QUOTE("A_SZ_QUOTE", "A_SZ_QUOTE"),
        A_SZ_Index_QUOTE("A_SZ_Index_QUOTE", "A_SZ_Index_QUOTE"),
        A_SH_Index_QUOTE("A_SH_Index_QUOTE", "A_SH_Index_QUOTE"),
        A_SH_Index_Stream("A_SH_Index_Stream", "A_SH_Index_Stream"),
        A_SZ_Index_Stream("A_SZ_Index_Stream", "A_SZ_Index_Stream"),
        A_QUOTE("A_Quote", "Quote"),
        A_INDEX_STATIC("A_Index_Static", "静态数据"),
        A_INDEX_DELAY("A_Index_Delay", "延迟数据"),
        A_INDEX_STREAM("A_Index_Stream", "实时数据"),

		// 工银品种
		HK_BMP_ICBCA("HK_BMP_ICBCA", "BMP"),
		HK_BMP_QUOTE_ICBCA("HK_BMP_Quote_ICBCA", "BMP&点击报价"),
		HK_STREAM_ICBCA("HK_Stream_ICBCA", "港股LV2串流报价"),
		US_NLS_ICBCA("US_NLS_ICBCA", "纳斯达克NLS报价"),
		US_BASIC_ICBCA("US_Basic_ICBCA", "纳斯达克最优报价(非专业)"),
		US_BASIC_PRO_ICBCA("US_Basic_pro_ICBCA", "纳斯达克最优报价(专业版)"),
		A_DELAY_ICBCA("A_Delay_ICBCA", "延迟行情"),
		A_QUOTE_ICBCA("A_Quote_ICBCA", "实时点击报价"),

		;


        private final String code;
        private final String info;

        QuotesTypeEnum(String code, String info) {
            this.code = code;
            this.info = info;
        }

        public String getCode() {
            return code;
        }

        public String getInfo() {
            return info;
        }
    }


    /**
     * 是否是专业投资者（美股）
     */
    public enum ProfessionalTypeEnum {
        YES(1, "是"), NO(0, "不是");
        private final int code;
        private final String info;
        ProfessionalTypeEnum(int code, String info) {
            this.code = code;
            this.info = info;
        }
        public int getCode() {
            return code;
        }
        public String getInfo() {
            return info;
        }
    }


    /**
     * 是否登录
     */
    public enum LoginStatusEnum {
        YES(1, "登录"), NO(0, "未登录");
        private final int code;
        private final String info;
        LoginStatusEnum(int code, String info) {
            this.code = code;
            this.info = info;
        }
        public int getCode() {
            return code;
        }
        public String getInfo() {
            return info;
        }
    }

    /**
     * 通用状态  Y  N
     */
    public enum StatusEnum {
        YES("Y", "Y"), NO("N", "N");
        private final String code;
        private final String info;
        StatusEnum(String code, String info) {
            this.code = code;
            this.info = info;
        }
        public String getCode() {
            return code;
        }
        public String getInfo() {
            return info;
        }
    }

    /**
     * 通用状态  0 无效 1 有效
     */
    public enum StatusIntEnum {
        NO(0, "无效"), YES(1, "有效");
        private final int code;
        private final String info;
        StatusIntEnum(int code, String info) {
            this.code = code;
            this.info = info;
        }
        public int getCode() {
            return code;
        }
        public String getInfo() {
            return info;
        }
    }

    /**
     * 通用状态  0 无效 1 有效
     */
    public enum StatusStringEnum {
        NO(0, "无效"), YES(1, "有效");
        private final Integer code;
        private final String info;
        StatusStringEnum(Integer code, String info) {
            this.code = code;
            this.info = info;
        }
        public Integer getCode() {
            return code;
        }
        public String getInfo() {
            return info;
        }
    }


    /**
     * 市场状态
     */
    public enum MarketStatusEnum {
        PRE_OPEN("PRE_OPEN", "盘前"), AFTER_OPEN("AFTER_OPEN", "盘后"),
        TRADING("TRADING", "交易中"), CLOSED("未开盘", "未开盘");
        private final String code;
        private final String info;
        MarketStatusEnum(String code, String info) {
            this.code = code;
            this.info = info;
        }
        public String getCode() {
            return code;
        }
        public String getInfo() {
            return info;
        }
    }


    /**
     * 品种市场
     */

    public enum QuotesMarketEnum {
        A(0, "A股"), HK(1, "港股"),
        US(2,"美股"), OTHER(3, "其他");

        private final Integer code;
        private final String info;

        QuotesMarketEnum(Integer code, String info) {
            this.code = code;
            this.info = info;
        }

        public Integer getCode() {
            return code;
        }

        public String getInfo() {
            return info;
        }
    }

    /**
     * 不同区域可访问的品种地区 (多对一)
     */
    public enum QuotesAreaJurisdictionEnum {
        // 0中国大陆 1中国香港 2全球 3非中国大陆 4其他
        CN(Arrays.asList("0","2"), "中国大陆", "CN", "CN"), HK(Arrays.asList("1","2","3"), "中国香港", "HK","HK"),
        TWN(Arrays.asList("2","3"),"中国台湾", "AR","TWN"),MO(Arrays.asList("2","3"),"中国澳门","AR", "MO"),
        UNCN(Arrays.asList("2","3"), "非中国", "UNCN","UNKNOWN"),
        ;

        private final List<String> areas;
        private final String info;
        private final String areaCode;
        private final String region;

        QuotesAreaJurisdictionEnum(List<String> areas, String info, String areaCode, String region) {
            this.areas = areas;
            this.info = info;
            this.areaCode = areaCode;
            this.region = region;
        }

        public List<String> getAreas() {
            return areas;
        }

        public String getInfo() {
            return info;
        }

        public String getAreaCode() {
            return areaCode;
        }

        public String getRegion() {
            return region;
        }

        public static String getAreasCode(String areaCode) {
            if (null == areaCode){
                return UNCN.getAreaCode();
            }

            for (QuotesAreaJurisdictionEnum c : QuotesAreaJurisdictionEnum.values()) {
                if (c.getAreaCode().equalsIgnoreCase(areaCode)) {
                    return c.getAreaCode();
                }
            }
            return UNCN.getAreaCode();
        }

        public static List<String> getAreas(String regionCode) {
            if (StringUtils.isBlank(regionCode)){
                return UNCN.getAreas();
            }

            for (QuotesAreaJurisdictionEnum c : QuotesAreaJurisdictionEnum.values()) {
                if (c.getRegion().equalsIgnoreCase(regionCode)) {
                    return c.getAreas();
                }
            }
            return UNCN.getAreas();
        }
    }

    /**
     * 不同行情品种地区标识对应包含的areaCode (一对多)
     */
    public enum QuotesAreaJurisdictionTeamEnum {
        // 0中国大陆 1中国香港 2全球 3非中国大陆 4其他
        CN("0", "CN"),
        HK("1", "HK"),
        ALL("2", "CN,HK,AR,UNCN"),
        UNCN("3", "HK,AR,UNCN"),
        OTH("4", "UNCN"),
        ;

        public static final Integer CN_VALUE = 0;
		public static final Integer HK_VALUE = 1;
		public static final Integer ALL_VALUE = 2;
		public static final Integer UNCN_VALUE = 3;
		public static final Integer OTH_VALUE = 4;

        private final String area;
        private final String areaCodes;

        public String getArea() {
            return area;
        }

        public String getAreaCodes() {
            return areaCodes;
        }

        QuotesAreaJurisdictionTeamEnum(String area, String areaCodes) {
            this.area = area;
            this.areaCodes = areaCodes;
        }

        public static String getAreaCodes(String area) {
            if (null == area){
                return OTH.getAreaCodes();
            }

            for (QuotesAreaJurisdictionTeamEnum c : QuotesAreaJurisdictionTeamEnum.values()) {
                if (c.getArea().equals(area)) {
                    return c.getAreaCodes();
                }
            }

            return UNCN.getAreaCodes();
        }
    }

    /**
     * 获取方式 [1=客户下单 2=系统赠送 3=手动赠送 4=活动赠送]
     */
    public enum OrderWayEnum {
        CLIENT_ORDER(1, "客户下单"),
        TX_PRESENTED_ORDER(2, "系统赠送"),
        MANUAL_PRESENTED_ORDER(3, "手动赠送"),
        ACTIVITY_ORDER(4, "活动赠送"),
        ;

        private final Integer code;
        private final String info;

        OrderWayEnum(Integer code, String info) {
            this.code = code;
            this.info = info;
        }

        public Integer getCode() {
            return code;
        }

        public String getInfo() {
            return info;
        }
    }

    /**
     * 订单来源
     */
    public enum OrderSourceEnum {
        yf_APP(1, "掌上智珠APP"),
        yf_PC(2, "掌上智珠PC"),
        MANUAL_PRESENTED(3, "人工赠送"),
        USER_SYNC_PRESENTED(4, "用户同步赠送"),
        ;

        private final Integer code;
        private final String info;

        OrderSourceEnum(Integer code, String info) {
            this.code = code;
            this.info = info;
        }

        public Integer getCode() {
            return code;
        }

        public String getInfo() {
            return info;
        }
    }

    /**
     * 支付方式
     */
    public enum payWayEnum {
        APPSTORE(1, "AppStore"),
        WECHAT(2, "WeChat"),
        ALIPAY(3, "AliPay"),
        UNIPAY(4, "UniPay"),
        CREDITS_EXCHANGE(5, "用户积分兑换"),
		CASH_ACCOUNT(6, "现金账户"),
        MARGIN_ACCOUNT(7, "融资账户"),
        OTHERS(0, "其他"),
        ;

        private final Integer code;
        private final String info;

        payWayEnum(Integer code, String info) {
            this.code = code;
            this.info = info;
        }

        public Integer getCode() {
            return code;
        }

        public String getInfo() {
            return info;
        }
    }

    /**
     * 品种和设备终端
     */
    public enum varietiesDeviceEnum {
        ALL(0, "全平台", "ALL"),
        WEB(1, "网站", "WEB"),
        APP(2, "app", "APP"),
        PC(3, "pc", "PC"),
        HIDDEN(4, "非展示", "HIDDEN"),
        OTHERS(5, "其他", "OTHERS"),
        ;

        private final Integer code;
        private final String info;
        private final String codeEn;

        varietiesDeviceEnum(Integer code, String info, String codeEn) {
            this.code = code;
            this.info = info;
            this.codeEn = codeEn;
        }

        public Integer getCode() {
            return code;
        }

        public String getInfo() {
            return info;
        }

        public String getCodeEn() {
            return codeEn;
        }

        public static Integer getCode(String codeEN) {
            if (null == codeEN){
                return null;
            }

            for (varietiesDeviceEnum c : varietiesDeviceEnum.values()) {
                if (c.getCodeEn().equalsIgnoreCase(codeEN)) {
                    return c.getCode();
                }
            }
            return null;
        }
    }

    /**
     * 订单失败原因
     */
    public enum OrderFailReasonEnum {
        TX_CLOSE(1, "人工关闭"),
        USER_CLOSE(2, "用户撤单"),
        AUTO_CLOSE(3, "消费完"),
        ;

        private final Integer code;
        private final String info;

        OrderFailReasonEnum(Integer code, String info) {
            this.code = code;
            this.info = info;
        }

        public Integer getCode() {
            return code;
        }

        public String getInfo() {
            return info;
        }
    }

    /**
     * 行情消费状态
     */
    public enum OrderConsumeStatusEnum {
        CONSUMPTION_NO("1", "待消费"),
        CONSUMPTION_IN("2", "消费中"),
        CONSUMPTION_END("3", "消费完"),
        ;

        private final String code;
        private final String info;

        OrderConsumeStatusEnum(String code, String info) {
            this.code = code;
            this.info = info;
        }

        public String getCode() {
            return code;
        }

        public String getInfo() {
            return info;
        }
    }

    /**
     * 品种子市场
     */
    public enum varietiesMarketSegmentsEnum {
        ALL("all", "全部"),
        SZ("sz", "深交所"),
        SH("sh", "上交所"),
        ;

        private final String code;
        private final String info;

        varietiesMarketSegmentsEnum(String code, String info) {
            this.code = code;
            this.info = info;
        }

        public String getCode() {
            return code;
        }

        public String getInfo() {
            return info;
        }
    }

	/**
	 * 行情品种更新类型 0-静态历史、1-延迟行情、2-手动刷新、3-实时行情
	 */
	public enum QuotesTypeUpdateType{
		UPDATE_TYPE_STATIC_HISTORY(0, "静态历史"),
		UPDATE_TYPE_DELAYED(1, "延迟行情"),
		UPDATE_TYPE_MANUAL_REFRESH(2, "手动刷新"),
		UPDATE_TYPE_ACTUAL_TIME(3, "实时行情"),
		;

		public static final Integer UPDATE_TYPE_STATIC_HISTORY_VALUE = 0;
		public static final Integer UPDATE_TYPE_DELAYED_VALUE = 1;
		public static final Integer UPDATE_TYPE_MANUAL_REFRESH_VALUE = 2;
		public static final Integer UPDATE_TYPE_ACTUAL_TIME_VALUE = 3;

		private final Integer code;
		private final String info;

		QuotesTypeUpdateType(Integer code, String info) {
			this.code = code;
			this.info = info;
		}
	}

	/**
	 * 行情品种类型 行情品种类型 0静态历史、1延迟行情、2BPM、3点击报价、4实时行情
	 */
	public enum QuotesTypeTypeEnum{
		TYPE_STATIC_HISTORY(0, "静态历史"),
		TYPE_DELAYED(1, "延迟行情"),
		TYPE_MANUAL_BPM(2, "BPM"),
		TYPE_MANUAL_CLICK(3, "点击报价"),
		TYPE_ACTUAL_TIME(4, "实时行情"),
		;

		public static final Integer TYPE_STATIC_HISTORY_VALUE = 0;
		public static final Integer TYPE_DELAYED_VALUE = 1;
		public static final Integer TYPE_MANUAL_BPM_VALUE = 2;
		public static final Integer TYPE_MANUAL_CLICK_VALUE = 3;
		public static final Integer TYPE_ACTUAL_TIME_VALUE = 4;

		private final Integer code;
		private final String info;

		QuotesTypeTypeEnum(Integer code, String info) {
			this.code = code;
			this.info = info;
		}

		public Integer getCode() {
			return code;
		}

		public String getInfo() {
			return info;
		}
	}

	/**
	 * 行情品种级别
	 */
	public enum QuotesTypeLevel{
		LV0(1, "LV0"),
		LV1(2, "LV1"),
		LV2(3, "LV2"),
		;

		public static final Integer LV0_VALUE = 1;
		public static final Integer LV1_VALUE = 2;
		public static final Integer LV2_VALUE = 3;

		private final Integer code;
		private final String info;

		QuotesTypeLevel(Integer code, String info) {
			this.code = code;
			this.info = info;
		}
	}

	/**
	 * 行情套餐市场
	 */
	public enum PackageMarketTypeEnum {
		A("0", "A股"), HK("1", "港股"),
		US("2","美股");

		private final String code;
		private final String info;

		PackageMarketTypeEnum(String code, String info) {
			this.code = code;
			this.info = info;
		}

		public String getCode() {
			return code;
		}

		public String getInfo() {
			return info;
		}
	}

	/**
	 * 我的行情-操作类型
	 */
	public enum OperateTypeEnum {
		RENEWAL(1, "续费"),
		UPGRADE(2, "升级"),
		;

		private final int code;
		private final String info;

		OperateTypeEnum(int code, String info) {
			this.code = code;
			this.info = info;
		}

		public int getCode() {
			return code;
		}

		public String getInfo() {
			return info;
		}
	}

	/**
	 * 行情品种级别
	 */
	public enum LevelEnum {
		L0(1, "L0"),
		L1(2, "L1"),
		L2(3, "L2"),
		;

		private final int code;
		private final String info;

		LevelEnum(int code, String info) {
			this.code = code;
			this.info = info;
		}

		public int getCode() {
			return code;
		}

		public String getInfo() {
			return info;
		}
	}

	/**
	 * 品种使用地区(0中国大陆 1中国香港 2全球 3非中国大陆 4其他)
	 */
	public enum varietiesAreaEnum {
		CN(0, "中国大陆"),
		HK(1, "中国香港"),
		ALL(2, "全球"),
		NO_CN(3, "非中国大陆"),
		OTHERS(4, "其他"),
		;

		private final Integer code;
		private final String info;

		varietiesAreaEnum(Integer code, String info) {
			this.code = code;
			this.info = info;
		}

		public Integer getCode() {
			return code;
		}

		public String getInfo() {
			return info;
		}
	}
}
