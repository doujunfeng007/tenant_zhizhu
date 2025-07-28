package com.minigod.zero.bpmn.module.account.service;

import com.minigod.zero.bpmn.module.account.vo.AccountBackReasonVO;
import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.bpmn.module.account.entity.AccountBackReasonEntity;

import java.util.List;

/**
 *  服务类
 *
 * @author Chill
 */
public interface IAccountBackReasonService extends BaseService<AccountBackReasonEntity> {

    List<AccountBackReasonVO> selectByApplicationId(String applicationId);

    List<String> selectCodeListByApplicationId(String applicationId);

    int deleteWithCode(String applicationId, String code);

    void deleteByApplicationId(String applicationId);

    AccountBackReasonVO selectByApplicationIdAndCode(String applicationId, String code);

    int updateByApplicationId(AccountBackReasonEntity bo);
}
