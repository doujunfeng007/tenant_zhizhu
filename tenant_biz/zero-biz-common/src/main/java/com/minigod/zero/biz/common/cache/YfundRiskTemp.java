package com.minigod.zero.biz.common.cache;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class YfundRiskTemp implements Serializable {
	/**
	 * 用户号
	 */
    private Long userId;
	/**
	 * 选项内容
	 */
    private String tempData;
	/**
	 * 保存时间
	 */
	private Date time;
}
