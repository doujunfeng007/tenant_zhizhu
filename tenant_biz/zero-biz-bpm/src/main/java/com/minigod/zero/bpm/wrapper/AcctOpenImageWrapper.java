package com.minigod.zero.bpm.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.bpm.entity.AcctOpenImageEntity;
import com.minigod.zero.bpm.vo.AcctOpenImageVO;
import java.util.Objects;

/**
 * 客户开户影像表 包装类,返回视图层所需的字段
 *
 * @author 掌上智珠
 * @since 2023-05-17
 */
public class AcctOpenImageWrapper extends BaseEntityWrapper<AcctOpenImageEntity, AcctOpenImageVO>  {

	public static AcctOpenImageWrapper build() {
		return new AcctOpenImageWrapper();
 	}

	@Override
	public AcctOpenImageVO entityVO(AcctOpenImageEntity acctOpenImage) {
	    AcctOpenImageVO acctOpenImageVO = new AcctOpenImageVO();
    	if (acctOpenImage != null) {
		    acctOpenImageVO = Objects.requireNonNull(BeanUtil.copy(acctOpenImage, AcctOpenImageVO.class));

		    //User createUser = UserCache.getUser(acctOpenImage.getCreateUser());
		    //User updateUser = UserCache.getUser(acctOpenImage.getUpdateUser());
		    //acctOpenImageVO.setCreateUserName(createUser.getName());
		    //acctOpenImageVO.setUpdateUserName(updateUser.getName());
        }
		return acctOpenImageVO;
	}


}
