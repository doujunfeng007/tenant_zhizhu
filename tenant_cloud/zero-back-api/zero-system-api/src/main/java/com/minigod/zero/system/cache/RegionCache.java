
package com.minigod.zero.system.cache;

import com.minigod.zero.system.entity.Region;
import com.minigod.zero.system.feign.ISysClient;
import com.minigod.zero.core.cache.utils.CacheUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.SpringUtil;

import static com.minigod.zero.core.cache.constant.CacheConstant.SYS_CACHE;

/**
 * 行政区划缓存工具类
 *
 * @author Chill
 */
public class RegionCache {
	public static final int PROVINCE_LEVEL = 1;
	public static final int CITY_LEVEL = 2;
	public static final int DISTRICT_LEVEL = 3;
	public static final int TOWN_LEVEL = 4;
	public static final int VILLAGE_LEVEL = 5;

	private static final String REGION_CODE = "region:code:";

	private static ISysClient sysClient;

	private static ISysClient getSysClient() {
		if (sysClient == null) {
			sysClient = SpringUtil.getBean(ISysClient.class);
		}
		return sysClient;
	}

	/**
	 * 获取行政区划实体
	 *
	 * @param code 区划编号
	 * @return Param
	 */
	public static Region getByCode(String code) {
		return CacheUtil.get(SYS_CACHE, REGION_CODE, code, () -> {
			R<Region> result = getSysClient().getRegion(code);
			return result.getData();
		});
	}

}
