package com.minigod.zero.bpmn.module.account.service;

import com.minigod.zero.bpmn.module.account.vo.AccountOperateLogVO;
import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.bpmn.module.account.entity.AccountOperateLogEntity;

import java.util.List;

/**
 *  服务类
 *
 * @author Chill
 */
public interface IAccountOperateLogService extends BaseService<AccountOperateLogEntity> {
    List<AccountOperateLogVO> queryListByApplicationId(String applicationId);

}
