package com.minigod.zero.bpmn.module.account.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.bpmn.module.account.entity.AccountOtherDisclosureEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountOtherDisclosureVO;

/**
 *  包装类,返回视图层所需的字段
 *
 * @author Chill
 */
public class AccountOtherDisclosureWrapper extends BaseEntityWrapper<AccountOtherDisclosureEntity, AccountOtherDisclosureVO>  {

	public static AccountOtherDisclosureWrapper build() {
		return new AccountOtherDisclosureWrapper();
 	}

	@Override
	public AccountOtherDisclosureVO entityVO(AccountOtherDisclosureEntity account_other_disclosure) {
		AccountOtherDisclosureVO account_other_disclosureVO = BeanUtil.copy(account_other_disclosure, AccountOtherDisclosureVO.class);

		//User createUser = UserCache.getUser(account_other_disclosure.getCreateUser());
		//User updateUser = UserCache.getUser(account_other_disclosure.getUpdateUser());
		//account_other_disclosureVO.setCreateUserName(createUser.getName());
		//account_other_disclosureVO.setUpdateUserName(updateUser.getName());

		return account_other_disclosureVO;
	}

}
