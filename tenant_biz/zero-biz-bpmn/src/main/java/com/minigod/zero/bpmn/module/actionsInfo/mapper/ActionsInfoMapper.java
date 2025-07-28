package com.minigod.zero.bpmn.module.actionsInfo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpmn.module.actionsInfo.bo.ActionsInfoBo;
import com.minigod.zero.bpmn.module.actionsInfo.entity.CorporateActionsInfoEntity;
import com.minigod.zero.bpmn.module.actionsInfo.vo.CorporateActionsInfoVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 公司行动记录表 Mapper 接口
 *
 * @author wengzejie
 * @since 2024-03-13
 */
public interface ActionsInfoMapper extends BaseMapper<CorporateActionsInfoEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page 分页对象
	 * @param bo 查询条件对象
	 * @return List<ActionsInfoVO>
	 */
	List<CorporateActionsInfoVO> selectActionsInfoPage(IPage page, @Param("query") ActionsInfoBo bo);


}
