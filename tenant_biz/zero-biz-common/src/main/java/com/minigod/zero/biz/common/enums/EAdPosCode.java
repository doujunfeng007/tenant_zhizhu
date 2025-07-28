package com.minigod.zero.biz.common.enums;

public enum EAdPosCode {
	CUSTOM("自定义广告", 1000),
	PTFNOTE("自媒体广场", 1001),
	ADVISERPLATFORM("自媒体后台", 1003),
	FINANCIAL("理财版本", 1005),
	INDEXAD("首页广告页", 1007),
	FRIENDAD("股友广告页", 1008),
	BROKERAD("经理人广告", 1009),
	OFFICIAL_MAINLAND("web官网首页-大陆", 1011),
	OFFICIAL_OVERSEAS("web官网首页-海外", 1012),
	HITNEWAD("打新活动页", 1013),
	HITNEWAD_OFC_MLAND("打新活动页-大陆官网", 1014),
	H5_OFFICIAL_MAINLAND("H5官网首页-大陆", 1015),
	H5_OFFICIAL_OVERSEAS("H5官网首页-海外", 1016),
	H5_NEWSAD("资讯详细广告页", 1017),
	MKTAD("交易底部TAB广告页", 1018),
	IFENGAD("凤凰网广告页", 1019),
	ENTERPRISE("企业专区", 1020),
	TRADE("交易页", 1030),
    Invite_Activity("邀请好友-成就页", 1031),
	HK_INVITE("香港邀请好友-成就页", 1032),


	INDEXPUSH("蒙层-弹窗", 1002),
	STARTUP("闪屏", 1006),
	NEWSTOCKTOBUY("蒙层-新股", 1010),
	INDIVIDUAL_STOCK_OPTIONAL("banner-自选", 1033),
	MARKET("banner-交易", 1034),
	NEWS("banner-资讯", 1035),
	ADMINISTER_FINANCIAL("banner-理财", 1036),
	MY_HOME_PAGE("banner-我的", 1037),

	OPEN_ACCOUNT_PAGE("banner-open-account", 1038),
	PI_PAGE("pi-account", 1039),
	;

	private String typeName;
	private Integer typeValue;

	private EAdPosCode(String typeName, Integer typeValue) {
		this.typeName = typeName;
		this.typeValue = typeValue;
	}

	public Integer getTypeValue() {
		return this.typeValue;
	}

	public static String getTypeName(Integer index) {
		for (EAdPosCode eAdPosCode : EAdPosCode.values()) {
			if (eAdPosCode.getTypeValue().equals(index)) {
				return eAdPosCode.typeName;
			}
		}
		return null;
	}

	public static Integer getTypeValue(Integer index) {
		for (EAdPosCode eAdPosCode : EAdPosCode.values()) {
			if (eAdPosCode.getTypeValue().equals(index)) {
				return eAdPosCode.getTypeValue();
			}
		}
		return null;
	}
}
