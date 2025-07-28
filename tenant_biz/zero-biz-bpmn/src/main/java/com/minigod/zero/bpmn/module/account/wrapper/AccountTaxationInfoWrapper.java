package com.minigod.zero.bpmn.module.account.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.bpmn.module.account.entity.AccountTaxationInfoEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountTaxationInfoVO;

/**
 *  包装类,返回视图层所需的字段
 *
 * @author Chill
 */
public class AccountTaxationInfoWrapper extends BaseEntityWrapper<AccountTaxationInfoEntity, AccountTaxationInfoVO>  {

	public static AccountTaxationInfoWrapper build() {
		return new AccountTaxationInfoWrapper();
 	}

	@Override
	public AccountTaxationInfoVO entityVO(AccountTaxationInfoEntity account_taxation_info) {
		AccountTaxationInfoVO account_taxation_infoVO = BeanUtil.copy(account_taxation_info, AccountTaxationInfoVO.class);

		//User createUser = UserCache.getUser(account_taxation_info.getCreateUser());
		//User updateUser = UserCache.getUser(account_taxation_info.getUpdateUser());
		//account_taxation_infoVO.setCreateUserName(createUser.getName());
		//account_taxation_infoVO.setUpdateUserName(updateUser.getName());

		return account_taxation_infoVO;
	}

}
