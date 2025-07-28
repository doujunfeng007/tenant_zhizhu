package com.minigod.zero.bpm.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.bpm.entity.AcctOpenOfflineEntity;
import com.minigod.zero.bpm.vo.AcctOpenOfflineVO;
import java.util.Objects;

/**
 * 线下开户-BPM回调记录表 包装类,返回视图层所需的字段
 *
 * @author 掌上智珠
 * @since 2023-05-17
 */
public class AcctOpenOfflineWrapper extends BaseEntityWrapper<AcctOpenOfflineEntity, AcctOpenOfflineVO>  {

	public static AcctOpenOfflineWrapper build() {
		return new AcctOpenOfflineWrapper();
 	}

	@Override
	public AcctOpenOfflineVO entityVO(AcctOpenOfflineEntity acctOpenOffline) {
	    AcctOpenOfflineVO acctOpenOfflineVO = new AcctOpenOfflineVO();
    	if (acctOpenOffline != null) {
		    acctOpenOfflineVO = Objects.requireNonNull(BeanUtil.copy(acctOpenOffline, AcctOpenOfflineVO.class));

		    //User createUser = UserCache.getUser(acctOpenOffline.getCreateUser());
		    //User updateUser = UserCache.getUser(acctOpenOffline.getUpdateUser());
		    //acctOpenOfflineVO.setCreateUserName(createUser.getName());
		    //acctOpenOfflineVO.setUpdateUserName(updateUser.getName());
        }
		return acctOpenOfflineVO;
	}


}
