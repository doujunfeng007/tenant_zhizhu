package com.minigod.zero.biz.common.enums;

import org.apache.commons.lang3.StringUtils;

public enum NewsSource {
	_ZQRB(1, "证券日报"),
	_ZQSB(2, "证券时报");

	private Integer val;
	private String desc;

	public Integer getVal() {
		return val;
	}

	public String getDesc() {
		return desc;
	}

	NewsSource(Integer val, String desc) {
		this.val = val;
		this.desc = desc;
	}

	public static final boolean isNewsSource(String datasource) {
		if (StringUtils.isNotBlank(datasource)){
			for (NewsSource newsSource : NewsSource.values()) {
				if (newsSource.getDesc().equals(datasource)) {
					return true;
				}
			}
		}
		return false;
	}
}
