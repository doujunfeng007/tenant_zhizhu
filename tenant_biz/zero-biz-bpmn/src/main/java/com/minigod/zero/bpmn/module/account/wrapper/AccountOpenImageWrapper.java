package com.minigod.zero.bpmn.module.account.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.bpmn.module.account.entity.AccountOpenImageEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountOpenImageVO;

/**
 *  包装类,返回视图层所需的字段
 *
 * @author Chill
 */
public class AccountOpenImageWrapper extends BaseEntityWrapper<AccountOpenImageEntity, AccountOpenImageVO>  {

	public static AccountOpenImageWrapper build() {
		return new AccountOpenImageWrapper();
 	}

	@Override
	public AccountOpenImageVO entityVO(AccountOpenImageEntity account_open_image) {
		AccountOpenImageVO account_open_imageVO = BeanUtil.copy(account_open_image, AccountOpenImageVO.class);

		//User createUser = UserCache.getUser(account_open_image.getCreateUser());
		//User updateUser = UserCache.getUser(account_open_image.getUpdateUser());
		//account_open_imageVO.setCreateUserName(createUser.getName());
		//account_open_imageVO.setUpdateUserName(updateUser.getName());

		return account_open_imageVO;
	}

}
