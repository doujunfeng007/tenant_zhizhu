package com.minigod.zero.bpmn.module.account.service;

import com.minigod.zero.bpmn.module.account.vo.AccountDepositInfoVO;
import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.bpmn.module.account.entity.AccountDepositInfoEntity;

/**
 * 服务类
 *
 * @author Chill
 */
public interface IAccountDepositInfoService extends BaseService<AccountDepositInfoEntity> {

	Boolean insert(AccountDepositInfoEntity openAccountDepositInfo);

	AccountDepositInfoVO queryByApplicationId(String applicationId);

	AccountDepositInfoVO queryByBankAccountNumber(String bankAccountNumber, String accountHolderName);

	void deleteByApplicationId(String applicationId);
}
