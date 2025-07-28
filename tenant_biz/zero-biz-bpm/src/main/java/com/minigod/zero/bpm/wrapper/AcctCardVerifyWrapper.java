package com.minigod.zero.bpm.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.bpm.entity.AcctCardVerifyEntity;
import com.minigod.zero.bpm.vo.AcctCardVerifyVO;
import java.util.Objects;

/**
 * 银行卡四要素验证信息表 包装类,返回视图层所需的字段
 *
 * @author 掌上智珠
 * @since 2023-05-17
 */
public class AcctCardVerifyWrapper extends BaseEntityWrapper<AcctCardVerifyEntity, AcctCardVerifyVO>  {

	public static AcctCardVerifyWrapper build() {
		return new AcctCardVerifyWrapper();
 	}

	@Override
	public AcctCardVerifyVO entityVO(AcctCardVerifyEntity acctCardVerify) {
	    AcctCardVerifyVO acctCardVerifyVO = new AcctCardVerifyVO();
    	if (acctCardVerify != null) {
		    acctCardVerifyVO = Objects.requireNonNull(BeanUtil.copy(acctCardVerify, AcctCardVerifyVO.class));

		    //User createUser = UserCache.getUser(acctCardVerify.getCreateUser());
		    //User updateUser = UserCache.getUser(acctCardVerify.getUpdateUser());
		    //acctCardVerifyVO.setCreateUserName(createUser.getName());
		    //acctCardVerifyVO.setUpdateUserName(updateUser.getName());
        }
		return acctCardVerifyVO;
	}


}
