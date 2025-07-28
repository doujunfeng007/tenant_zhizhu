package com.minigod.zero.bpm.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.bpm.entity.AcctOpenInfoEntity;
import com.minigod.zero.bpm.vo.AcctOpenInfoVO;
import java.util.Objects;

/**
 * 开户申请信息表 包装类,返回视图层所需的字段
 *
 * @author 掌上智珠
 * @since 2023-05-17
 */
public class AcctOpenInfoWrapper extends BaseEntityWrapper<AcctOpenInfoEntity, AcctOpenInfoVO>  {

	public static AcctOpenInfoWrapper build() {
		return new AcctOpenInfoWrapper();
 	}

	@Override
	public AcctOpenInfoVO entityVO(AcctOpenInfoEntity acctOpenInfo) {
	    AcctOpenInfoVO acctOpenInfoVO = new AcctOpenInfoVO();
    	if (acctOpenInfo != null) {
		    acctOpenInfoVO = Objects.requireNonNull(BeanUtil.copy(acctOpenInfo, AcctOpenInfoVO.class));

		    //User createUser = UserCache.getUser(acctOpenInfo.getCreateUser());
		    //User updateUser = UserCache.getUser(acctOpenInfo.getUpdateUser());
		    //acctOpenInfoVO.setCreateUserName(createUser.getName());
		    //acctOpenInfoVO.setUpdateUserName(updateUser.getName());
        }
		return acctOpenInfoVO;
	}


}
