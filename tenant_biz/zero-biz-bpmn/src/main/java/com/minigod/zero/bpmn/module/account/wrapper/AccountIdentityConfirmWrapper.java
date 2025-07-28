package com.minigod.zero.bpmn.module.account.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.bpmn.module.account.entity.AccountIdentityConfirmEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountIdentityConfirmVO;

/**
 *  包装类,返回视图层所需的字段
 *
 * @author Chill
 */
public class AccountIdentityConfirmWrapper extends BaseEntityWrapper<AccountIdentityConfirmEntity, AccountIdentityConfirmVO>  {

	public static AccountIdentityConfirmWrapper build() {
		return new AccountIdentityConfirmWrapper();
 	}

	@Override
	public AccountIdentityConfirmVO entityVO(AccountIdentityConfirmEntity account_identity_confirm) {
		AccountIdentityConfirmVO account_identity_confirmVO = BeanUtil.copy(account_identity_confirm, AccountIdentityConfirmVO.class);

		//User createUser = UserCache.getUser(account_identity_confirm.getCreateUser());
		//User updateUser = UserCache.getUser(account_identity_confirm.getUpdateUser());
		//account_identity_confirmVO.setCreateUserName(createUser.getName());
		//account_identity_confirmVO.setUpdateUserName(updateUser.getName());

		return account_identity_confirmVO;
	}

}
