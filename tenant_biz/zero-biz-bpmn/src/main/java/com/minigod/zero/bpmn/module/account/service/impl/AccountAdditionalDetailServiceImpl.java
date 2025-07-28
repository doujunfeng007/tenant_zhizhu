package com.minigod.zero.bpmn.module.account.service.impl;

import com.minigod.zero.bpmn.module.account.vo.AccountAdditionalDetailVO;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.bpmn.module.account.entity.AccountAdditionalDetailEntity;
import com.minigod.zero.bpmn.module.account.mapper.AccountAdditionalDetailMapper;
import com.minigod.zero.bpmn.module.account.service.IAccountAdditionalDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  开户补充资料信息备注服务实现类
 *
 * @author Chill
 */
@Service
public class AccountAdditionalDetailServiceImpl extends BaseServiceImpl<AccountAdditionalDetailMapper, AccountAdditionalDetailEntity> implements IAccountAdditionalDetailService {

    @Override
    public List<AccountAdditionalDetailVO> queryListByApplicationId(String applicationId) {
        return baseMapper.queryListByApplicationId(applicationId);
    }
}
