
package com.minigod.zero.develop.service;

import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.develop.entity.ModelPrototype;

import java.util.List;

/**
 * 数据原型表 服务类
 *
 * @author Chill
 */
public interface IModelPrototypeService extends BaseService<ModelPrototype> {

	/**
	 * 批量提交
	 *
	 * @param modelPrototypes 原型集合
	 * @return boolean
	 */
	boolean submitList(List<ModelPrototype> modelPrototypes);

	/**
	 * 原型列表
	 *
	 * @param modelId 模型ID
	 * @return List<ModelPrototype>
	 */
	List<ModelPrototype> prototypeList(Long modelId);

}
