
package com.minigod.zero.flow.engine.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.flow.engine.entity.FlowModel;

import java.util.List;

/**
 * FlowMapper.
 *
 * @author Chill
 */
public interface FlowMapper extends BaseMapper<FlowModel> {

	/**
	 * 自定义分页
	 * @param page
	 * @param flowModel
	 * @return
	 */
	List<FlowModel> selectFlowPage(IPage page, FlowModel flowModel);

	/**
	 * 获取模型
	 * @param parentModelId
	 * @return
	 */
	List<FlowModel> findByParentModelId(String parentModelId);
}
