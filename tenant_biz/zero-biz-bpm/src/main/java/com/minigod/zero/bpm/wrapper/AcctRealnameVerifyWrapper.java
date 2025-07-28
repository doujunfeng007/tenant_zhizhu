package com.minigod.zero.bpm.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.bpm.entity.AcctRealnameVerifyEntity;
import com.minigod.zero.bpm.vo.AcctRealnameVerifyVO;
import java.util.Objects;

/**
 * 用户实名认证表 包装类,返回视图层所需的字段
 *
 * @author 掌上智珠
 * @since 2023-05-17
 */
public class AcctRealnameVerifyWrapper extends BaseEntityWrapper<AcctRealnameVerifyEntity, AcctRealnameVerifyVO>  {

	public static AcctRealnameVerifyWrapper build() {
		return new AcctRealnameVerifyWrapper();
 	}

	@Override
	public AcctRealnameVerifyVO entityVO(AcctRealnameVerifyEntity acctRealnameVerify) {
	    AcctRealnameVerifyVO acctRealnameVerifyVO = new AcctRealnameVerifyVO();
    	if (acctRealnameVerify != null) {
		    acctRealnameVerifyVO = Objects.requireNonNull(BeanUtil.copy(acctRealnameVerify, AcctRealnameVerifyVO.class));

		    //User createUser = UserCache.getUser(acctRealnameVerify.getCreateUser());
		    //User updateUser = UserCache.getUser(acctRealnameVerify.getUpdateUser());
		    //acctRealnameVerifyVO.setCreateUserName(createUser.getName());
		    //acctRealnameVerifyVO.setUpdateUserName(updateUser.getName());
        }
		return acctRealnameVerifyVO;
	}


}
