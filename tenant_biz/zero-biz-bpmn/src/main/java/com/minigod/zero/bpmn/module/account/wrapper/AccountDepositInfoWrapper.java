package com.minigod.zero.bpmn.module.account.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.bpmn.module.account.entity.AccountDepositInfoEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountDepositInfoVO;

/**
 *  包装类,返回视图层所需的字段
 *
 * @author Chill
 */
public class AccountDepositInfoWrapper extends BaseEntityWrapper<AccountDepositInfoEntity, AccountDepositInfoVO>  {

	public static AccountDepositInfoWrapper build() {
		return new AccountDepositInfoWrapper();
 	}

	@Override
	public AccountDepositInfoVO entityVO(AccountDepositInfoEntity account_deposit_info) {
		AccountDepositInfoVO account_deposit_infoVO = BeanUtil.copy(account_deposit_info, AccountDepositInfoVO.class);

		//User createUser = UserCache.getUser(account_deposit_info.getCreateUser());
		//User updateUser = UserCache.getUser(account_deposit_info.getUpdateUser());
		//account_deposit_infoVO.setCreateUserName(createUser.getName());
		//account_deposit_infoVO.setUpdateUserName(updateUser.getName());

		return account_deposit_infoVO;
	}

}
