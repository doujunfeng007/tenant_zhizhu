package com.minigod.zero.bpmn.module.account.service.impl;

import com.minigod.zero.bpmn.module.account.vo.AccountDepositInfoVO;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.bpmn.module.account.entity.AccountDepositInfoEntity;
import com.minigod.zero.bpmn.module.account.mapper.AccountDepositInfoMapper;
import com.minigod.zero.bpmn.module.account.service.IAccountDepositInfoService;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author Chill
 */
@Service
public class AccountDepositInfoServiceImpl extends BaseServiceImpl<AccountDepositInfoMapper, AccountDepositInfoEntity> implements IAccountDepositInfoService {

	@Override
	public Boolean insert(AccountDepositInfoEntity openAccountDepositInfo) {
		return baseMapper.insert(openAccountDepositInfo) > 0;
	}

	@Override
	public AccountDepositInfoVO queryByApplicationId(String applicationId) {
		return baseMapper.queryByApplicationId(applicationId);
	}

	@Override
	public AccountDepositInfoVO queryByBankAccountNumber(String bankAccountNumber, String accountHolderName) {
		return baseMapper.queryByBankAccountNumber(bankAccountNumber, accountHolderName);
	}

	@Override
	public void deleteByApplicationId(String applicationId) {
		baseMapper.deleteByApplicationId(applicationId);
	}
}
