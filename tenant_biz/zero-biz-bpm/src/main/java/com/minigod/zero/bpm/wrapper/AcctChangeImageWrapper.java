package com.minigod.zero.bpm.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.bpm.entity.AcctChangeImageEntity;
import com.minigod.zero.bpm.vo.AcctChangeImageVO;
import java.util.Objects;

/**
 * 客户修改资料图片表 包装类,返回视图层所需的字段
 *
 * @author 掌上智珠
 * @since 2023-05-17
 */
public class AcctChangeImageWrapper extends BaseEntityWrapper<AcctChangeImageEntity, AcctChangeImageVO>  {

	public static AcctChangeImageWrapper build() {
		return new AcctChangeImageWrapper();
 	}

	@Override
	public AcctChangeImageVO entityVO(AcctChangeImageEntity acctChangeImage) {
	    AcctChangeImageVO acctChangeImageVO = new AcctChangeImageVO();
    	if (acctChangeImage != null) {
		    acctChangeImageVO = Objects.requireNonNull(BeanUtil.copy(acctChangeImage, AcctChangeImageVO.class));

		    //User createUser = UserCache.getUser(acctChangeImage.getCreateUser());
		    //User updateUser = UserCache.getUser(acctChangeImage.getUpdateUser());
		    //acctChangeImageVO.setCreateUserName(createUser.getName());
		    //acctChangeImageVO.setUpdateUserName(updateUser.getName());
        }
		return acctChangeImageVO;
	}


}
