package com.minigod.zero.manage.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.manage.entity.DiscoverIconEntity;
import com.minigod.zero.manage.vo.DiscoverIconVO;

import java.util.Objects;

/**
 * 焦点功能区图标管理 包装类,返回视图层所需的字段
 *
 * @author 掌上智珠
 * @since 2023-03-21
 */
public class DiscoverIconWrapper extends BaseEntityWrapper<DiscoverIconEntity, DiscoverIconVO>  {

	public static DiscoverIconWrapper build() {
		return new DiscoverIconWrapper();
 	}

	@Override
	public DiscoverIconVO entityVO(DiscoverIconEntity DiscoverIcon) {
	    DiscoverIconVO DiscoverIconVO = new DiscoverIconVO();
    	if (DiscoverIcon != null) {
		    DiscoverIconVO = Objects.requireNonNull(BeanUtil.copy(DiscoverIcon, DiscoverIconVO.class));

		    //User createUser = UserCache.getUser(DiscoverIcon.getCreateUser());
		    //User updateUser = UserCache.getUser(DiscoverIcon.getUpdateUser());
		    //DiscoverIconVO.setCreateUserName(createUser.getName());
		    //DiscoverIconVO.setUpdateUserName(updateUser.getName());
        }
		return DiscoverIconVO;
	}


}
