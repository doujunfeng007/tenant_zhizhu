package com.minigod.zero.bpmn.module.account.service;

import com.minigod.zero.bpmn.module.account.vo.AccountOtherDisclosureVO;
import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.bpmn.module.account.entity.AccountOtherDisclosureEntity;

import java.util.List;

/**
 *  服务类
 *
 * @author Chill
 */
public interface IAccountOtherDisclosureService extends BaseService<AccountOtherDisclosureEntity> {
    List<AccountOtherDisclosureVO> queryListByApplicationId(String applicationId);

}
