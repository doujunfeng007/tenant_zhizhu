
package com.minigod.zero.system.service;

import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.system.entity.Param;

/**
 * 服务类
 *
 * @author Chill
 */
public interface IParamService extends BaseService<Param> {

	/**
	 * 获取参数值
	 *
	 * @param paramKey 参数key
	 * @return String
	 */
	String getValue(String paramKey);

}
