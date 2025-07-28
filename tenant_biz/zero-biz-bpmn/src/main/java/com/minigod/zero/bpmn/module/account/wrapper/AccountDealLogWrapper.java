package com.minigod.zero.bpmn.module.account.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.bpmn.module.account.entity.AccountDealLogEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountDealLogVO;

/**
 *  包装类,返回视图层所需的字段
 *
 * @author Chill
 */
public class AccountDealLogWrapper extends BaseEntityWrapper<AccountDealLogEntity, AccountDealLogVO>  {

	public static AccountDealLogWrapper build() {
		return new AccountDealLogWrapper();
 	}

	@Override
	public AccountDealLogVO entityVO(AccountDealLogEntity account_deal_log) {
		AccountDealLogVO account_deal_logVO = BeanUtil.copy(account_deal_log, AccountDealLogVO.class);

		//User createUser = UserCache.getUser(account_deal_log.getCreateUser());
		//User updateUser = UserCache.getUser(account_deal_log.getUpdateUser());
		//account_deal_logVO.setCreateUserName(createUser.getName());
		//account_deal_logVO.setUpdateUserName(updateUser.getName());

		return account_deal_logVO;
	}

}
