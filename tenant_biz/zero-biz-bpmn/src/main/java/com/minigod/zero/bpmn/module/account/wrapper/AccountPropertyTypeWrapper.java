package com.minigod.zero.bpmn.module.account.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.bpmn.module.account.entity.AccountPropertyTypeEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountPropertyTypeVO;

/**
 *  包装类,返回视图层所需的字段
 *
 * @author Chill
 */
public class AccountPropertyTypeWrapper extends BaseEntityWrapper<AccountPropertyTypeEntity, AccountPropertyTypeVO>  {

	public static AccountPropertyTypeWrapper build() {
		return new AccountPropertyTypeWrapper();
 	}

	@Override
	public AccountPropertyTypeVO entityVO(AccountPropertyTypeEntity account_property_type) {
		AccountPropertyTypeVO account_property_typeVO = BeanUtil.copy(account_property_type, AccountPropertyTypeVO.class);

		//User createUser = UserCache.getUser(account_property_type.getCreateUser());
		//User updateUser = UserCache.getUser(account_property_type.getUpdateUser());
		//account_property_typeVO.setCreateUserName(createUser.getName());
		//account_property_typeVO.setUpdateUserName(updateUser.getName());

		return account_property_typeVO;
	}

}
