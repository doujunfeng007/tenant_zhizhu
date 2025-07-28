package com.minigod.zero.bpmn.module.account.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.bpmn.module.account.entity.AccountAdditionalFileEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountAdditionalFileVO;

/**
 *  包装类,返回视图层所需的字段
 *
 * @author Chill
 */
public class AccountAdditionalFileWrapper extends BaseEntityWrapper<AccountAdditionalFileEntity, AccountAdditionalFileVO>  {

	public static AccountAdditionalFileWrapper build() {
		return new AccountAdditionalFileWrapper();
 	}

	@Override
	public AccountAdditionalFileVO entityVO(AccountAdditionalFileEntity account_additional_file) {
		AccountAdditionalFileVO account_additional_fileVO = BeanUtil.copy(account_additional_file, AccountAdditionalFileVO.class);

		//User createUser = UserCache.getUser(account_additional_file.getCreateUser());
		//User updateUser = UserCache.getUser(account_additional_file.getUpdateUser());
		//account_additional_fileVO.setCreateUserName(createUser.getName());
		//account_additional_fileVO.setUpdateUserName(updateUser.getName());

		return account_additional_fileVO;
	}

}
