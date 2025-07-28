package com.minigod.zero.bpmn.module.account.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpmn.module.account.bo.query.CaQuery;
import com.minigod.zero.bpmn.module.account.vo.AccountCaVerityInfoVO;
import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.bpmn.module.account.entity.AccountCaVerityInfoEntity;

/**
 *  服务类
 *
 * @author Chill
 */
public interface IAccountCaVerityInfoService extends BaseService<AccountCaVerityInfoEntity> {

    IPage<AccountCaVerityInfoVO> queryPageList(IPage<Object> page, CaQuery caQuery);

    AccountCaVerityInfoVO queryByApplicationId(String applicationId);
}
