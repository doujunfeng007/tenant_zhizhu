package com.minigod.zero.bpmn.module.account.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.bpmn.module.account.entity.AccountBankVerityInfoEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountBankVerityInfoVO;

/**
 *  包装类,返回视图层所需的字段
 *
 * @author Chill
 */
public class AccountBankVerityInfoWrapper extends BaseEntityWrapper<AccountBankVerityInfoEntity, AccountBankVerityInfoVO>  {

	public static AccountBankVerityInfoWrapper build() {
		return new AccountBankVerityInfoWrapper();
 	}

	@Override
	public AccountBankVerityInfoVO entityVO(AccountBankVerityInfoEntity account_bank_verity_info) {
		AccountBankVerityInfoVO account_bank_verity_infoVO = BeanUtil.copy(account_bank_verity_info, AccountBankVerityInfoVO.class);

		//User createUser = UserCache.getUser(account_bank_verity_info.getCreateUser());
		//User updateUser = UserCache.getUser(account_bank_verity_info.getUpdateUser());
		//account_bank_verity_infoVO.setCreateUserName(createUser.getName());
		//account_bank_verity_infoVO.setUpdateUserName(updateUser.getName());

		return account_bank_verity_infoVO;
	}

}
