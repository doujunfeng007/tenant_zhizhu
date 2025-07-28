package com.minigod.zero.bpmn.module.actionsInfo.service.impl;

import com.minigod.zero.bpmn.module.actionsInfo.bo.ActionsInfoBo;
import com.minigod.zero.bpmn.module.actionsInfo.entity.CorporateActionsInfoEntity;
import com.minigod.zero.bpmn.module.actionsInfo.mapper.ActionsInfoMapper;
import com.minigod.zero.bpmn.module.actionsInfo.service.ICorporateActionsInfoService;
import com.minigod.zero.bpmn.module.actionsInfo.vo.CorporateActionsInfoVO;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 公司行动记录表 服务实现类
 *
 * @author wengzejie
 * @since 2024-03-13
 */
@Service
public class ActionsInfoServiceImpl extends BaseServiceImpl<ActionsInfoMapper, CorporateActionsInfoEntity> implements ICorporateActionsInfoService {

	@Override
	public IPage<CorporateActionsInfoVO> selectActionsInfoPage(IPage<CorporateActionsInfoVO> page, ActionsInfoBo bo) {
		return page.setRecords(baseMapper.selectActionsInfoPage(page, bo));
	}


}
