package com.minigod.zero.manage.wrapper;

import com.minigod.zero.manage.entity.AdInfoEntity;
import com.minigod.zero.manage.vo.AdInfoVO;
import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;

import java.util.Objects;

/**
 * 广告信息表 包装类,返回视图层所需的字段
 *
 * @author 掌上智珠
 * @since 2023-03-18
 */
public class AdInfoWrapper extends BaseEntityWrapper<AdInfoEntity, AdInfoVO>  {

	public static AdInfoWrapper build() {
		return new AdInfoWrapper();
 	}

	@Override
	public AdInfoVO entityVO(AdInfoEntity adInfo) {
	    AdInfoVO adInfoVO = new AdInfoVO();
    	if (adInfo != null) {
		    adInfoVO = Objects.requireNonNull(BeanUtil.copy(adInfo, AdInfoVO.class));

		    //User createUser = UserCache.getUser(adInfo.getCreateUser());
		    //User updateUser = UserCache.getUser(adInfo.getUpdateUser());
		    //adInfoVO.setCreateUserName(createUser.getName());
		    //adInfoVO.setUpdateUserName(updateUser.getName());
        }
		return adInfoVO;
	}


}
