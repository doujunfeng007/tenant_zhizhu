package com.minigod.zero.bpmn.module.account.service.impl;

import com.minigod.zero.bpmn.module.account.vo.AccountIdentityConfirmVO;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.bpmn.module.account.entity.AccountIdentityConfirmEntity;
import com.minigod.zero.bpmn.module.account.mapper.AccountIdentityConfirmMapper;
import com.minigod.zero.bpmn.module.account.service.IAccountIdentityConfirmService;
import org.springframework.stereotype.Service;

/**
 *  服务实现类
 *
 * @author Chill
 */
@Service
public class AccountIdentityConfirmServiceImpl extends BaseServiceImpl<AccountIdentityConfirmMapper, AccountIdentityConfirmEntity> implements IAccountIdentityConfirmService {

    @Override
    public AccountIdentityConfirmVO queryByApplicationId(String applicationId) {
        return baseMapper.queryByApplicationId(applicationId);
    }
}
