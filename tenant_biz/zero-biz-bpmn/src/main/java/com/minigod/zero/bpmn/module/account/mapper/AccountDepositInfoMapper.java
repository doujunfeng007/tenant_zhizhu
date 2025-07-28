package com.minigod.zero.bpmn.module.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.bpmn.module.account.entity.AccountDepositInfoEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountDepositInfoVO;

/**
 * Mapper 接口
 *
 * @author Chill
 */
public interface AccountDepositInfoMapper extends BaseMapper<AccountDepositInfoEntity> {
	AccountDepositInfoVO queryByApplicationId(String applicationId);

	AccountDepositInfoVO queryByBankAccountNumber(String bankAccountNumber, String accountHolderName);

	void deleteByApplicationId(String applicationId);
}
