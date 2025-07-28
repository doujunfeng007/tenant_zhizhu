
package com.minigod.zero.develop.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.develop.entity.ModelPrototype;
import com.minigod.zero.develop.mapper.ModelPrototypeMapper;
import com.minigod.zero.develop.service.IModelPrototypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 数据原型表 服务实现类
 *
 * @author Chill
 */
@Service
public class ModelPrototypeServiceImpl extends BaseServiceImpl<ModelPrototypeMapper, ModelPrototype> implements IModelPrototypeService {

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean submitList(List<ModelPrototype> modelPrototypes) {
		modelPrototypes.forEach(modelPrototype -> {
			if (modelPrototype.getId() == null) {
				this.save(modelPrototype);
			} else {
				this.updateById(modelPrototype);
			}
		});
		return true;
	}

	@Override
	public List<ModelPrototype> prototypeList(Long modelId) {
		return this.list(Wrappers.<ModelPrototype>lambdaQuery().eq(ModelPrototype::getModelId, modelId));
	}

}
