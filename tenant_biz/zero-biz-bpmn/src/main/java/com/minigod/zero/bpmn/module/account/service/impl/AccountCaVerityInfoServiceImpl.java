package com.minigod.zero.bpmn.module.account.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpmn.module.account.bo.query.CaQuery;
import com.minigod.zero.bpmn.module.account.vo.AccountCaVerityInfoVO;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.bpmn.module.account.entity.AccountCaVerityInfoEntity;
import com.minigod.zero.bpmn.module.account.mapper.AccountCaVerityInfoMapper;
import com.minigod.zero.bpmn.module.account.service.IAccountCaVerityInfoService;
import org.springframework.stereotype.Service;

/**
 *  服务实现类
 *
 * @author Chill
 */
@Service
public class AccountCaVerityInfoServiceImpl extends BaseServiceImpl<AccountCaVerityInfoMapper, AccountCaVerityInfoEntity> implements IAccountCaVerityInfoService {

    @Override
    public IPage<AccountCaVerityInfoVO> queryPageList(IPage<Object> page, CaQuery caQuery) {
        return baseMapper.queryPageList(page,caQuery);
    }

    @Override
    public AccountCaVerityInfoVO queryByApplicationId(String applicationId) {
        return baseMapper.queryByApplicationId(applicationId);
    }
}
