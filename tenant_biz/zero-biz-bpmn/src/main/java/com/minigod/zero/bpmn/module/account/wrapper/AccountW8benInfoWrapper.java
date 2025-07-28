package com.minigod.zero.bpmn.module.account.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.bpmn.module.account.entity.AccountW8benInfoEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountW8benInfoVO;

/**
 *  包装类,返回视图层所需的字段
 *
 * @author Chill
 */
public class AccountW8benInfoWrapper extends BaseEntityWrapper<AccountW8benInfoEntity, AccountW8benInfoVO>  {

	public static AccountW8benInfoWrapper build() {
		return new AccountW8benInfoWrapper();
 	}

	@Override
	public AccountW8benInfoVO entityVO(AccountW8benInfoEntity account_w8ben_info) {
		AccountW8benInfoVO account_w8ben_infoVO = BeanUtil.copy(account_w8ben_info, AccountW8benInfoVO.class);

		//User createUser = UserCache.getUser(account_w8ben_info.getCreateUser());
		//User updateUser = UserCache.getUser(account_w8ben_info.getUpdateUser());
		//account_w8ben_infoVO.setCreateUserName(createUser.getName());
		//account_w8ben_infoVO.setUpdateUserName(updateUser.getName());

		return account_w8ben_infoVO;
	}

}
