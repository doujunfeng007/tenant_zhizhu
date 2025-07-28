package com.minigod.zero.bpmn.module.actionsInfo.service;

import com.minigod.zero.bpmn.module.actionsInfo.bo.ActionsInfoBo;
import com.minigod.zero.bpmn.module.actionsInfo.entity.CorporateActionsInfoEntity;
import com.minigod.zero.bpmn.module.actionsInfo.vo.CorporateActionsInfoVO;
import com.minigod.zero.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 公司行动记录表 服务类
 *
 * @author wengzejie
 * @since 2024-03-13
 */
public interface ICorporateActionsInfoService extends BaseService<CorporateActionsInfoEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page 分页对象
	 * @param bo 查询条件对象
	 * @return IPage<ActionsInfoVO>
	 */
	IPage<CorporateActionsInfoVO> selectActionsInfoPage(IPage<CorporateActionsInfoVO> page, ActionsInfoBo bo);


}
