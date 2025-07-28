package com.minigod.zero.bpm.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.bpm.entity.AcctOpenScheduleTempEntity;
import com.minigod.zero.bpm.vo.AcctOpenScheduleTempVO;
import java.util.Objects;

/**
 * 前端开户进度缓存表 包装类,返回视图层所需的字段
 *
 * @author 掌上智珠
 * @since 2023-05-17
 */
public class AcctOpenScheduleTempWrapper extends BaseEntityWrapper<AcctOpenScheduleTempEntity, AcctOpenScheduleTempVO>  {

	public static AcctOpenScheduleTempWrapper build() {
		return new AcctOpenScheduleTempWrapper();
 	}

	@Override
	public AcctOpenScheduleTempVO entityVO(AcctOpenScheduleTempEntity acctOpenScheduleTemp) {
	    AcctOpenScheduleTempVO acctOpenScheduleTempVO = new AcctOpenScheduleTempVO();
    	if (acctOpenScheduleTemp != null) {
		    acctOpenScheduleTempVO = Objects.requireNonNull(BeanUtil.copy(acctOpenScheduleTemp, AcctOpenScheduleTempVO.class));

		    //User createUser = UserCache.getUser(acctOpenScheduleTemp.getCreateUser());
		    //User updateUser = UserCache.getUser(acctOpenScheduleTemp.getUpdateUser());
		    //acctOpenScheduleTempVO.setCreateUserName(createUser.getName());
		    //acctOpenScheduleTempVO.setUpdateUserName(updateUser.getName());
        }
		return acctOpenScheduleTempVO;
	}


}
