package com.minigod.zero.bpmn.module.account.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.bpmn.module.account.entity.AccountOpenApplicationEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountOpenApplicationVO;

/**
 *  包装类,返回视图层所需的字段
 *
 * @author Chill
 */
public class AccountOpenApplicationWrapper extends BaseEntityWrapper<AccountOpenApplicationEntity, AccountOpenApplicationVO>  {

	public static AccountOpenApplicationWrapper build() {
		return new AccountOpenApplicationWrapper();
 	}

	@Override
	public AccountOpenApplicationVO entityVO(AccountOpenApplicationEntity account_open_application) {
		AccountOpenApplicationVO account_open_applicationVO = BeanUtil.copy(account_open_application, AccountOpenApplicationVO.class);

		//User createUser = UserCache.getUser(account_open_application.getCreateUser());
		//User updateUser = UserCache.getUser(account_open_application.getUpdateUser());
		//account_open_applicationVO.setCreateUserName(createUser.getName());
		//account_open_applicationVO.setUpdateUserName(updateUser.getName());

		return account_open_applicationVO;
	}

}
