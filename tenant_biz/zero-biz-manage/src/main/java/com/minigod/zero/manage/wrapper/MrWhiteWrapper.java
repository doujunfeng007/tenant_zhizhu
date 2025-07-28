package com.minigod.zero.manage.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.manage.entity.MrWhiteEntity;
import com.minigod.zero.manage.vo.MrWhiteVO;
import java.util.Objects;

/**
 * MR白名单 包装类,返回视图层所需的字段
 *
 * @author 掌上智珠
 * @since 2023-03-23
 */
public class MrWhiteWrapper extends BaseEntityWrapper<MrWhiteEntity, MrWhiteVO>  {

	public static MrWhiteWrapper build() {
		return new MrWhiteWrapper();
 	}

	@Override
	public MrWhiteVO entityVO(MrWhiteEntity mrWhite) {
	    MrWhiteVO mrWhiteVO = new MrWhiteVO();
    	if (mrWhite != null) {
		    mrWhiteVO = Objects.requireNonNull(BeanUtil.copy(mrWhite, MrWhiteVO.class));

		    //User createUser = UserCache.getUser(mrWhite.getCreateUser());
		    //User updateUser = UserCache.getUser(mrWhite.getUpdateUser());
		    //mrWhiteVO.setCreateUserName(createUser.getName());
		    //mrWhiteVO.setUpdateUserName(updateUser.getName());
        }
		return mrWhiteVO;
	}


}
