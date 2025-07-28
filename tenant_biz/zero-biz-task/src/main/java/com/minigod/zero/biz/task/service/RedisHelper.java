package com.minigod.zero.biz.task.service;

import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.tool.beans.StkInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RedisHelper {

	@Resource
	private ZeroRedis zeroRedis;

	public String getAssetIdByStockCode(String stockCode, String mktCode) {
		String assetId = "";
		if (StringUtils.isNotEmpty(stockCode) &&
			StringUtils.isNotEmpty(mktCode)) {
			assetId = stockCode + "." + mktCode;
			if (zeroRedis.protoGet(assetId, StkInfo.class) == null) {
				assetId = stockCode + ".IDX." + mktCode;
				if (zeroRedis.protoGet(assetId, StkInfo.class) == null) {
					assetId = "";
				}
			}
		}
		return assetId;
	}
}
