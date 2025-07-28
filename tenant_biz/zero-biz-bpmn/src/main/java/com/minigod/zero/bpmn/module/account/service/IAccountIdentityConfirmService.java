package com.minigod.zero.bpmn.module.account.service;

import com.minigod.zero.bpmn.module.account.vo.AccountIdentityConfirmVO;
import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.bpmn.module.account.entity.AccountIdentityConfirmEntity;

/**
 *  服务类
 *
 * @author Chill
 */
public interface IAccountIdentityConfirmService extends BaseService<AccountIdentityConfirmEntity> {
    AccountIdentityConfirmVO queryByApplicationId(String applicationId);

}
