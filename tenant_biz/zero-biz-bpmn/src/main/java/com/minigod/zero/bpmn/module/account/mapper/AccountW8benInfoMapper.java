package com.minigod.zero.bpmn.module.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.bpmn.module.account.entity.AccountW8benInfoEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountW8benInfoVO;

/**
 *  Mapper 接口
 *
 * @author Chill
 */
public interface AccountW8benInfoMapper extends BaseMapper<AccountW8benInfoEntity> {

    AccountW8benInfoVO queryByApplicationId(String applicationId);

    void deleteByApplicationId(String applicationId);
}
