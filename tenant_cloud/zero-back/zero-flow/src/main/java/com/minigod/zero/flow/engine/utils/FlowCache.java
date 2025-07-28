
package com.minigod.zero.flow.engine.utils;

import com.minigod.zero.flow.engine.entity.FlowProcess;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.flowable.engine.repository.ProcessDefinition;
import com.minigod.zero.core.cache.utils.CacheUtil;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.core.tool.utils.Func;
import com.minigod.zero.core.tool.utils.SpringUtil;
import com.minigod.zero.core.tool.utils.StringPool;
import com.minigod.zero.system.cache.DictCache;

import static com.minigod.zero.core.cache.constant.CacheConstant.FLOW_CACHE;

/**
 * 流程缓存
 *
 * @author Chill
 */
public class FlowCache {

	private static final String FLOW_DEFINITION_ID = "definition:id:";
	private static RepositoryService repositoryService;

	private static RepositoryService getRepositoryService() {
		if (repositoryService == null) {
			repositoryService = SpringUtil.getBean(RepositoryService.class);
		}
		return repositoryService;
	}

	/**
	 * 获得流程定义对象
	 *
	 * @param processDefinitionId 流程对象id
	 * @return
	 */
	public static FlowProcess getProcessDefinition(String processDefinitionId) {
		return CacheUtil.get(FLOW_CACHE, FLOW_DEFINITION_ID, processDefinitionId, () -> {
			ProcessDefinition processDefinition = getRepositoryService().createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
			ProcessDefinitionEntityImpl processDefinitionEntity = BeanUtil.copy(processDefinition, ProcessDefinitionEntityImpl.class);
			return new FlowProcess(processDefinitionEntity);
		});
	}

	/**
	 * 获取流程类型名
	 *
	 * @param category 流程类型
	 * @return
	 */
	public static String getCategoryName(String category) {
		String[] categoryArr = category.split(StringPool.UNDERSCORE);
		if (categoryArr.length <= 1) {
			return StringPool.EMPTY;
		} else {
			return DictCache.getValue(category.split(StringPool.UNDERSCORE)[0], Func.toInt(category.split(StringPool.UNDERSCORE)[1]));
		}
	}

}
