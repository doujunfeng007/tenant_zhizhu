package com.minigod.zero.bpm.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.bpm.entity.AcctChangeInfoTempEntity;
import com.minigod.zero.bpm.vo.AcctChangeInfoTempVO;
import java.util.Objects;

/**
 * 客户证券资料修改缓存表 包装类,返回视图层所需的字段
 *
 * @author 掌上智珠
 * @since 2023-05-17
 */
public class AcctChangeInfoTempWrapper extends BaseEntityWrapper<AcctChangeInfoTempEntity, AcctChangeInfoTempVO>  {

	public static AcctChangeInfoTempWrapper build() {
		return new AcctChangeInfoTempWrapper();
 	}

	@Override
	public AcctChangeInfoTempVO entityVO(AcctChangeInfoTempEntity acctChangeInfoTemp) {
	    AcctChangeInfoTempVO acctChangeInfoTempVO = new AcctChangeInfoTempVO();
    	if (acctChangeInfoTemp != null) {
		    acctChangeInfoTempVO = Objects.requireNonNull(BeanUtil.copy(acctChangeInfoTemp, AcctChangeInfoTempVO.class));

		    //User createUser = UserCache.getUser(acctChangeInfoTemp.getCreateUser());
		    //User updateUser = UserCache.getUser(acctChangeInfoTemp.getUpdateUser());
		    //acctChangeInfoTempVO.setCreateUserName(createUser.getName());
		    //acctChangeInfoTempVO.setUpdateUserName(updateUser.getName());
        }
		return acctChangeInfoTempVO;
	}


}
