package com.minigod.zero.bpm.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.bpm.entity.AcctOpenVerifyEntity;
import com.minigod.zero.bpm.vo.AcctOpenVerifyVO;
import java.util.Objects;

/**
 * 客户认证记录表 包装类,返回视图层所需的字段
 *
 * @author 掌上智珠
 * @since 2023-05-17
 */
public class AcctOpenVerifyWrapper extends BaseEntityWrapper<AcctOpenVerifyEntity, AcctOpenVerifyVO>  {

	public static AcctOpenVerifyWrapper build() {
		return new AcctOpenVerifyWrapper();
 	}

	@Override
	public AcctOpenVerifyVO entityVO(AcctOpenVerifyEntity acctOpenVerify) {
	    AcctOpenVerifyVO acctOpenVerifyVO = new AcctOpenVerifyVO();
    	if (acctOpenVerify != null) {
		    acctOpenVerifyVO = Objects.requireNonNull(BeanUtil.copy(acctOpenVerify, AcctOpenVerifyVO.class));

		    //User createUser = UserCache.getUser(acctOpenVerify.getCreateUser());
		    //User updateUser = UserCache.getUser(acctOpenVerify.getUpdateUser());
		    //acctOpenVerifyVO.setCreateUserName(createUser.getName());
		    //acctOpenVerifyVO.setUpdateUserName(updateUser.getName());
        }
		return acctOpenVerifyVO;
	}


}
