package com.minigod.zero.bpmn.module.account.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.bpmn.module.account.entity.AccountCaVerityInfoEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountCaVerityInfoVO;

/**
 *  包装类,返回视图层所需的字段
 *
 * @author Chill
 */
public class AccountCaVerityInfoWrapper extends BaseEntityWrapper<AccountCaVerityInfoEntity, AccountCaVerityInfoVO>  {

	public static AccountCaVerityInfoWrapper build() {
		return new AccountCaVerityInfoWrapper();
 	}

	@Override
	public AccountCaVerityInfoVO entityVO(AccountCaVerityInfoEntity account_ca_verity_info) {
		AccountCaVerityInfoVO account_ca_verity_infoVO = BeanUtil.copy(account_ca_verity_info, AccountCaVerityInfoVO.class);

		//User createUser = UserCache.getUser(account_ca_verity_info.getCreateUser());
		//User updateUser = UserCache.getUser(account_ca_verity_info.getUpdateUser());
		//account_ca_verity_infoVO.setCreateUserName(createUser.getName());
		//account_ca_verity_infoVO.setUpdateUserName(updateUser.getName());

		return account_ca_verity_infoVO;
	}

}
