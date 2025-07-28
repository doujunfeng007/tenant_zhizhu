package com.minigod.zero.bpmn.module.account.service;

import com.minigod.zero.bpmn.module.account.vo.AccountW8benInfoVO;
import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.bpmn.module.account.entity.AccountW8benInfoEntity;

/**
 * W-8BEN表格美国税务局信息服务类
 *
 * @author Chill
 */
public interface IAccountW8benInfoService extends BaseService<AccountW8benInfoEntity> {

	AccountW8benInfoVO queryByApplicationId(String applicationId);
}
