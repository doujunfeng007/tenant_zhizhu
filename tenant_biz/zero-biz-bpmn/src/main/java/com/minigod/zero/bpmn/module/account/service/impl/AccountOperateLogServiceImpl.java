package com.minigod.zero.bpmn.module.account.service.impl;

import com.minigod.zero.bpmn.module.account.vo.AccountOperateLogVO;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.bpmn.module.account.entity.AccountOperateLogEntity;
import com.minigod.zero.bpmn.module.account.mapper.AccountOperateLogMapper;
import com.minigod.zero.bpmn.module.account.service.IAccountOperateLogService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  服务实现类
 *
 * @author Chill
 */
@Service
public class AccountOperateLogServiceImpl extends BaseServiceImpl<AccountOperateLogMapper, AccountOperateLogEntity> implements IAccountOperateLogService {

    @Override
    public List<AccountOperateLogVO> queryListByApplicationId(String applicationId) {
        return baseMapper.queryListByApplicationId(applicationId);
    }
}
