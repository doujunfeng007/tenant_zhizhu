package com.minigod.zero.bpmn.module.account.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.bpmn.module.account.entity.AccountRiskDisclosureEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountRiskDisclosureVO;

/**
 *  包装类,返回视图层所需的字段
 *
 * @author Chill
 */
public class AccountRiskDisclosureWrapper extends BaseEntityWrapper<AccountRiskDisclosureEntity, AccountRiskDisclosureVO>  {

	public static AccountRiskDisclosureWrapper build() {
		return new AccountRiskDisclosureWrapper();
 	}

	@Override
	public AccountRiskDisclosureVO entityVO(AccountRiskDisclosureEntity account_risk_disclosure) {
		AccountRiskDisclosureVO account_risk_disclosureVO = BeanUtil.copy(account_risk_disclosure, AccountRiskDisclosureVO.class);

		//User createUser = UserCache.getUser(account_risk_disclosure.getCreateUser());
		//User updateUser = UserCache.getUser(account_risk_disclosure.getUpdateUser());
		//account_risk_disclosureVO.setCreateUserName(createUser.getName());
		//account_risk_disclosureVO.setUpdateUserName(updateUser.getName());

		return account_risk_disclosureVO;
	}

}
