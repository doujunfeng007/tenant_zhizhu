package com.minigod.zero.bpmn.module.account.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.bpmn.module.account.entity.AccountOpenInfoEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountOpenInfoVO;

/**
 *  包装类,返回视图层所需的字段
 *
 * @author Chill
 */
public class AccountOpenInfoWrapper extends BaseEntityWrapper<AccountOpenInfoEntity, AccountOpenInfoVO>  {

	public static AccountOpenInfoWrapper build() {
		return new AccountOpenInfoWrapper();
 	}

	@Override
	public AccountOpenInfoVO entityVO(AccountOpenInfoEntity account_open_info) {
		AccountOpenInfoVO account_open_infoVO = BeanUtil.copy(account_open_info, AccountOpenInfoVO.class);

		//User createUser = UserCache.getUser(account_open_info.getCreateUser());
		//User updateUser = UserCache.getUser(account_open_info.getUpdateUser());
		//account_open_infoVO.setCreateUserName(createUser.getName());
		//account_open_infoVO.setUpdateUserName(updateUser.getName());

		return account_open_infoVO;
	}

}
