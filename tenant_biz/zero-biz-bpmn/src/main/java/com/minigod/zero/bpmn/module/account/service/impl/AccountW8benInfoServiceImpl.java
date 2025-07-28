package com.minigod.zero.bpmn.module.account.service.impl;

import com.minigod.zero.bpmn.module.account.vo.AccountW8benInfoVO;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.bpmn.module.account.entity.AccountW8benInfoEntity;
import com.minigod.zero.bpmn.module.account.mapper.AccountW8benInfoMapper;
import com.minigod.zero.bpmn.module.account.service.IAccountW8benInfoService;
import org.springframework.stereotype.Service;

/**
 *  服务实现类
 *
 * @author Chill
 */
@Service
public class AccountW8benInfoServiceImpl extends BaseServiceImpl<AccountW8benInfoMapper, AccountW8benInfoEntity> implements IAccountW8benInfoService {

    @Override
    public AccountW8benInfoVO queryByApplicationId(String applicationId) {
        return baseMapper.queryByApplicationId(applicationId);
    }
}
