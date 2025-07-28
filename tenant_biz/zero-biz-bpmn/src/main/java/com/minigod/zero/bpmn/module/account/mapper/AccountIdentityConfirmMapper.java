package com.minigod.zero.bpmn.module.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.bpmn.module.account.entity.AccountIdentityConfirmEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountIdentityConfirmVO;

/**
 *  Mapper 接口
 *
 * @author Chill
 */
public interface AccountIdentityConfirmMapper extends BaseMapper<AccountIdentityConfirmEntity> {

    AccountIdentityConfirmVO queryByApplicationId(String applicationId);

    void deleteByApplicationId(String applicationId);
}
