package com.minigod.zero.bpmn.module.account.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.bpmn.module.account.entity.AccountAdditionalDetailEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountAdditionalDetailVO;

/**
 *  包装类,返回视图层所需的字段
 *
 * @author Chill
 */
public class AccountAdditionalDetailWrapper extends BaseEntityWrapper<AccountAdditionalDetailEntity, AccountAdditionalDetailVO>  {

	public static AccountAdditionalDetailWrapper build() {
		return new AccountAdditionalDetailWrapper();
 	}

	@Override
	public AccountAdditionalDetailVO entityVO(AccountAdditionalDetailEntity account_additional_detail) {
		AccountAdditionalDetailVO account_additional_detailVO = BeanUtil.copy(account_additional_detail, AccountAdditionalDetailVO.class);

		//User createUser = UserCache.getUser(account_additional_detail.getCreateUser());
		//User updateUser = UserCache.getUser(account_additional_detail.getUpdateUser());
		//account_additional_detailVO.setCreateUserName(createUser.getName());
		//account_additional_detailVO.setUpdateUserName(updateUser.getName());

		return account_additional_detailVO;
	}

}
