/**
 * @Title: NewsEnums.java
 * @Copyright: © 2015 sunline
 * @Company: sunline
 */

package com.minigod.zero.biz.common.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @description
 *
 * @author sunline
 * @date 2015-7-20 上午9:45:38
 * @version v1.0
 */

public enum ENewsEnums {
	STK_NEWS("个股新闻", 1), //
	PUBLIC_REPORT("公告", 2), //
	RESEARCH_REPORT("研报", 3), //
	LABEL_NEWS("概念新闻", 4), //
	INDU_NEWS("行业新闻", 5), //
	IMPORTANT_NEWS("要闻", 6), //
	LIVE_NEWS("直播", 7), //
	HEADLINE_NEWS("头条", 8), //
	HOT_NEWS("热点资讯", 9),
	COLUMN_NEWS("专栏资讯", 10),
	CHOICE_NEWS("精选", 15),
	INDEX_NEWS("指数", 16),
	HK_NEWS("港股资讯", 17),
	US_NEWS("美股资讯", 18),
	FINET_NEWS("财华社资讯", 19),
	PHOENIX_NEWS("凤凰网资讯专栏", 20),
	PHOENIXHK_NEWS("凤凰网港股", 21),
	PUBLIC_NEWS("普通资讯", 22),
	INVESTMENT_ANALYSIS("研究报告", 23), //
	;

	private String typeName;
	private int typeValue;

	private ENewsEnums(String typeName, Integer typeValue) {
		this.typeName = typeName;
		this.typeValue = typeValue;
	}

	public int getTypeValue() {
		return this.typeValue;
	}

	public String getTypeName() {
		return typeName;
	}


	static final Map<String, MainNews> map = new HashMap<String, MainNews>();
	public static enum MainNews {
		_HW("海外", 6),
		_XG("香港", 6),
		_ND("内地", 6),
		_HY("行业", 6),
		_GD("观点", 6),
		_YW("要闻", 6),

		_NB("公告", 2),

		_HG("宏观", 3),
		_CL("策略", 3),
		_GG("个股", 3),
		_YJ("研究", 3),

		_SJ("数据", 11),
		_YD("异动", 11),
		_PB("盘报", 11),
		_SC("市场", 11),

		_HK("港股", 12),
		_US("美股", 12),
		_GS("公司", 12),

		_ZT("专题", 13),

		_JH("精华", 14),
		_DX("动向", 14),
		_NG("新股", 14)
	;
		private String val;
		private int desc;
		private MainNews(String val, int desc) {
			this.val = val;
			this.desc = desc;
			map.put(val, this);
		}

		public String val() {
			return val;
		}

		public int desc(){
			return desc;
		}

		public static final int getValue(String key) {
			MainNews code = map.get( key);
			if (code == null) {
				return 0;
			}
			return code.desc();
		}

	}
}
