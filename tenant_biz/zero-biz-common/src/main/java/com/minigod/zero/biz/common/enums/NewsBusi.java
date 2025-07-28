package com.minigod.zero.biz.common.enums;

import org.apache.commons.lang3.StringUtils;

public enum NewsBusi {
	_FT(1, "富途证券"),
	_QS(2, "青石证券"),
	_LH(3, "老虎证券"),
	_SKT(4, "史考特"),
	_DYZJ(5, "第一证券"),
	_MBJR(6, "美豹金融"),
	_MMZJ(7, "米盟证券"),
	_XY(8, "雪盈证券"),
	_XQ(9, "雪球");

	private Integer val;
	private String desc;

	public Integer getVal() {
		return val;
	}

	public String getDesc() {
		return desc;
	}

	NewsBusi(Integer val, String desc) {
		this.val = val;
		this.desc = desc;
	}

	public static final boolean isNewsbusi(String newsTitle) {
		if (StringUtils.isNotBlank(newsTitle)){
			for (NewsBusi newsBusi : NewsBusi.values()) {
				if (newsBusi.getDesc().equals(newsTitle)){
					return true;
				}
			}
		}
		return false;
	}
}
