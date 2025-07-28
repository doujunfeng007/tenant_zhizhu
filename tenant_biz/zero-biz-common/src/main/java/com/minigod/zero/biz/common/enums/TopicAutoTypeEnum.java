package com.minigod.zero.biz.common.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 专题自动化策略
 */
public enum TopicAutoTypeEnum {

	NEW_STOCK_OBSERVATION(100000088, "新股观察", Arrays.asList("新股","精华","动向","新股动向","新股深度")),
	STOCK_PRICE_MOVEMENT(100000089, "股价移动", Arrays.asList("异动","美股异动")),
	DAHANG_INVESTMENT_RESEARCH(100000090, "大行投研", Arrays.asList("大行研究", "港股研报精读", "智通元宇中小市值研究", "研究动向", "研究深度", "个股研究", "研究深度", "创投市场研究")),
	MARKET_ANALYSIS(100000091, "市场分析", Arrays.asList("海外深度","港澳深度","综合深度","内地深度")),
	INVESTMENT(100000092, "投资决策", Arrays.asList("策略","决策参考","港股研报精读","港股概念追踪","财报","重大","股权","融资","人事","停复牌")),
	BIGNEWS(100000093, "大新闻", Arrays.asList("港澳动向","海外动向","内地深度","综合深度","综合动向","海外深度","港澳深度","内地动向")),
	STOCK_CALENDAR(100000094, "股票日历", Arrays.asList("早知道"));

	private int topicId;
	private String desc;
	private List<String> category;

	TopicAutoTypeEnum(int topicId, String desc, List<String> category) {
		this.topicId = topicId;
		this.desc = desc;
		this.category = category;
	}

	public String getDesc() {
		return desc;
	}

	public int getTopicId() {
		return topicId;
	}

	public List<String> getCategory() {
		return category;
	}

	public static TopicAutoTypeEnum getTopicId(String category){
		if (StringUtils.isBlank(category)){
			return null;
		}

		for (TopicAutoTypeEnum topicAutoTypeEnum: TopicAutoTypeEnum.values()){
			List<String> categoryList = topicAutoTypeEnum.getCategory();
			if (categoryList.contains(category.trim())){
				return topicAutoTypeEnum;
			}
		}
		return null;
	}


	/**
	 * 获取多个专题
	 * @param category
	 * @return
	 */
	public static Set<TopicAutoTypeEnum> getMultiTopicId(String category){
		Set<TopicAutoTypeEnum> topicAutoTypeEnums = new HashSet<>();
		if (StringUtils.isNotBlank(category)){
			for (TopicAutoTypeEnum topicAutoTypeEnum: TopicAutoTypeEnum.values()){
				List<String> categoryList = topicAutoTypeEnum.getCategory();
				if (categoryList.contains(category.trim())){
					topicAutoTypeEnums.add(topicAutoTypeEnum);
				}
			}
		}
		return topicAutoTypeEnums;
	}
}
