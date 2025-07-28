package com.minigod.zero.bpmn.module.account.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.bpmn.module.account.entity.AccountOpenAgreementEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountOpenAgreementVO;

/**
 *  包装类,返回视图层所需的字段
 *
 * @author Chill
 */
public class AccountOpenAgreementWrapper extends BaseEntityWrapper<AccountOpenAgreementEntity, AccountOpenAgreementVO>  {

	public static AccountOpenAgreementWrapper build() {
		return new AccountOpenAgreementWrapper();
 	}

	@Override
	public AccountOpenAgreementVO entityVO(AccountOpenAgreementEntity account_open_agreement) {
		AccountOpenAgreementVO account_open_agreementVO = BeanUtil.copy(account_open_agreement, AccountOpenAgreementVO.class);

		//User createUser = UserCache.getUser(account_open_agreement.getCreateUser());
		//User updateUser = UserCache.getUser(account_open_agreement.getUpdateUser());
		//account_open_agreementVO.setCreateUserName(createUser.getName());
		//account_open_agreementVO.setUpdateUserName(updateUser.getName());

		return account_open_agreementVO;
	}

}
