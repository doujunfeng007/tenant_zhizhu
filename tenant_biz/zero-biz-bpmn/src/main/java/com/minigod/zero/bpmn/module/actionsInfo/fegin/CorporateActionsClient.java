package com.minigod.zero.bpmn.module.actionsInfo.fegin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.minigod.zero.bpmn.module.actionsInfo.service.ICorporateActionsInfoService;
import com.minigod.zero.bpmn.module.actionsInfo.client.ICorporateActionsClient;
import com.minigod.zero.bpmn.module.actionsInfo.dto.CorporateActionsInfoDTO;
import com.minigod.zero.bpmn.module.actionsInfo.entity.CorporateActionsInfoEntity;
import com.minigod.zero.core.tenant.annotation.NonDS;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.BeanUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @author wzj
 * @ClassName CorporateActionsClient.java
 * @Description TODO
 * @createTime 2024年3月15日18:10:32
 */
@Slf4j
@NonDS
@RestController
@RequiredArgsConstructor
public class CorporateActionsClient implements ICorporateActionsClient {

	@Autowired
	private ICorporateActionsInfoService corporateActionsInfoService;

	@Override
	public R saveActions(CorporateActionsInfoDTO dto) {
		CorporateActionsInfoEntity corporateActionsInfoEntity = new CorporateActionsInfoEntity();
		BeanUtil.copyProperties(dto, corporateActionsInfoEntity);
		corporateActionsInfoEntity.setCreateTime(new Date());
		corporateActionsInfoEntity.setStatus(0);
		corporateActionsInfoService.getBaseMapper().insert(corporateActionsInfoEntity);
		return R.success();
	}

	@Override
	public R<List<CorporateActionsInfoEntity>> getActions(CorporateActionsInfoDTO dto) {
		LambdaQueryWrapper<CorporateActionsInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(CorporateActionsInfoEntity::getTradeAccount, dto.getTradeAccount());
		queryWrapper.orderByDesc(CorporateActionsInfoEntity::getCreateTime);
		List<CorporateActionsInfoEntity> list = corporateActionsInfoService.list(queryWrapper);
		return R.data(list);
	}
}
