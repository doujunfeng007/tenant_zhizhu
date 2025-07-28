package com.minigod.zero.bpmn.module.account.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.bpmn.module.account.entity.AccountOperateLogEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountOperateLogVO;

/**
 *  包装类,返回视图层所需的字段
 *
 * @author Chill
 */
public class AccountOperateLogWrapper extends BaseEntityWrapper<AccountOperateLogEntity, AccountOperateLogVO>  {

	public static AccountOperateLogWrapper build() {
		return new AccountOperateLogWrapper();
 	}

	@Override
	public AccountOperateLogVO entityVO(AccountOperateLogEntity account_operate_log) {
		AccountOperateLogVO account_operate_logVO = BeanUtil.copy(account_operate_log, AccountOperateLogVO.class);

		//User createUser = UserCache.getUser(account_operate_log.getCreateUser());
		//User updateUser = UserCache.getUser(account_operate_log.getUpdateUser());
		//account_operate_logVO.setCreateUserName(createUser.getName());
		//account_operate_logVO.setUpdateUserName(updateUser.getName());

		return account_operate_logVO;
	}

}
