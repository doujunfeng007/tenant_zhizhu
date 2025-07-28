
package com.minigod.zero.system.cache;

import com.minigod.zero.system.entity.Param;
import com.minigod.zero.system.feign.ISysClient;
import com.minigod.zero.core.cache.utils.CacheUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.SpringUtil;

import static com.minigod.zero.core.cache.constant.CacheConstant.PARAM_CACHE;

/**
 * 参数缓存工具类
 *
 * @author Chill
 */
public class ParamCache {

	private static final String PARAM_ID = "param:id:";
	private static final String PARAM_VALUE = "param:value:";

	private static ISysClient sysClient;

	private static ISysClient getSysClient() {
		if (sysClient == null) {
			sysClient = SpringUtil.getBean(ISysClient.class);
		}
		return sysClient;
	}

	/**
	 * 获取参数实体
	 *
	 * @param id 主键
	 * @return Param
	 */
	public static Param getById(Long id) {
		return CacheUtil.get(PARAM_CACHE, PARAM_ID, id, () -> {
			R<Param> result = getSysClient().getParam(id);
			return result.getData();
		});
	}

	/**
	 * 获取参数配置
	 *
	 * @param paramKey 参数值
	 * @return String
	 */
	public static String getValue(String paramKey) {
		return CacheUtil.get(PARAM_CACHE, PARAM_VALUE, paramKey, () -> {
			R<String> result = getSysClient().getParamValue(paramKey);
			return result.getData();
		});
	}

}
