package com.minigod.zero.bpmn.module.actionsInfo.wrapper;

import com.minigod.zero.bpmn.module.actionsInfo.entity.CorporateActionsInfoEntity;
import com.minigod.zero.bpmn.module.actionsInfo.vo.CorporateActionsInfoVO;
import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import java.util.Objects;

/**
 * 公司行动记录表 包装类,返回视图层所需的字段
 *
 * @author wengzejie
 * @since 2024-03-13
 */
public class ActionsInfoWrapper extends BaseEntityWrapper<CorporateActionsInfoEntity, CorporateActionsInfoVO>  {

	public static ActionsInfoWrapper build() {
		return new ActionsInfoWrapper();
 	}

	@Override
	public CorporateActionsInfoVO entityVO(CorporateActionsInfoEntity actionsInfo) {
	    CorporateActionsInfoVO actionsInfoVO = new CorporateActionsInfoVO();
    	if (actionsInfo != null) {
		    actionsInfoVO = Objects.requireNonNull(BeanUtil.copy(actionsInfo, CorporateActionsInfoVO.class));

        }
		return actionsInfoVO;
	}


}
