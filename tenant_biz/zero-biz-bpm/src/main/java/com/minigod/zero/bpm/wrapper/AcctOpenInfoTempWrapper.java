package com.minigod.zero.bpm.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.bpm.entity.AcctOpenInfoTempEntity;
import com.minigod.zero.bpm.vo.AcctOpenInfoTempVO;
import java.util.Objects;

/**
 * 开户申请-客户填写信息缓存表 包装类,返回视图层所需的字段
 *
 * @author 掌上智珠
 * @since 2023-05-17
 */
public class AcctOpenInfoTempWrapper extends BaseEntityWrapper<AcctOpenInfoTempEntity, AcctOpenInfoTempVO>  {

	public static AcctOpenInfoTempWrapper build() {
		return new AcctOpenInfoTempWrapper();
 	}

	@Override
	public AcctOpenInfoTempVO entityVO(AcctOpenInfoTempEntity acctOpenInfoTemp) {
	    AcctOpenInfoTempVO acctOpenInfoTempVO = new AcctOpenInfoTempVO();
    	if (acctOpenInfoTemp != null) {
		    acctOpenInfoTempVO = Objects.requireNonNull(BeanUtil.copy(acctOpenInfoTemp, AcctOpenInfoTempVO.class));

		    //User createUser = UserCache.getUser(acctOpenInfoTemp.getCreateUser());
		    //User updateUser = UserCache.getUser(acctOpenInfoTemp.getUpdateUser());
		    //acctOpenInfoTempVO.setCreateUserName(createUser.getName());
		    //acctOpenInfoTempVO.setUpdateUserName(updateUser.getName());
        }
		return acctOpenInfoTempVO;
	}


}
