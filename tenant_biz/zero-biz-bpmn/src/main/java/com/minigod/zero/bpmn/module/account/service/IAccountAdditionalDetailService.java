package com.minigod.zero.bpmn.module.account.service;

import com.minigod.zero.bpmn.module.account.vo.AccountAdditionalDetailVO;
import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.bpmn.module.account.entity.AccountAdditionalDetailEntity;

import java.util.List;

/**
 *  开户补充资料信息备注服务类
 *
 * @author Chill
 */
public interface IAccountAdditionalDetailService extends BaseService<AccountAdditionalDetailEntity> {

    List<AccountAdditionalDetailVO> queryListByApplicationId(String applicationId);
}
