package com.minigod.zero.bpmn.module.account.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.bpmn.module.account.entity.AccountBackReasonEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountBackReasonVO;

/**
 *  包装类,返回视图层所需的字段
 *
 * @author Chill
 */
public class AccountBackReasonWrapper extends BaseEntityWrapper<AccountBackReasonEntity, AccountBackReasonVO>  {

	public static AccountBackReasonWrapper build() {
		return new AccountBackReasonWrapper();
 	}

	@Override
	public AccountBackReasonVO entityVO(AccountBackReasonEntity account_back_reason) {
		AccountBackReasonVO account_back_reasonVO = BeanUtil.copy(account_back_reason, AccountBackReasonVO.class);

		//User createUser = UserCache.getUser(account_back_reason.getCreateUser());
		//User updateUser = UserCache.getUser(account_back_reason.getUpdateUser());
		//account_back_reasonVO.setCreateUserName(createUser.getName());
		//account_back_reasonVO.setUpdateUserName(updateUser.getName());

		return account_back_reasonVO;
	}

}
