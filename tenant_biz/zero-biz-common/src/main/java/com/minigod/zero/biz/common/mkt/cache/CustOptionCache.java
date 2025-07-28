package com.minigod.zero.biz.common.mkt.cache;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户自选分组缓存
 */
@Data
public class CustOptionCache implements Serializable {
	private Long custId;
	private long updateTimes;
	private String content;
}
