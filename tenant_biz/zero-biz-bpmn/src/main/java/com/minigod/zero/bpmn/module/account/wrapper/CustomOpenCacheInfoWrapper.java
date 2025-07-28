package com.minigod.zero.bpmn.module.account.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.bpmn.module.account.entity.CustomOpenCacheInfoEntity;
import com.minigod.zero.bpmn.module.account.vo.CustomOpenCacheInfoVO;

/**
 *  包装类,返回视图层所需的字段
 *
 * @author Chill
 */
public class CustomOpenCacheInfoWrapper extends BaseEntityWrapper<CustomOpenCacheInfoEntity, CustomOpenCacheInfoVO>  {

	public static CustomOpenCacheInfoWrapper build() {
		return new CustomOpenCacheInfoWrapper();
 	}

	@Override
	public CustomOpenCacheInfoVO entityVO(CustomOpenCacheInfoEntity custom_open_cache_info) {
		CustomOpenCacheInfoVO custom_open_cache_infoVO = BeanUtil.copy(custom_open_cache_info, CustomOpenCacheInfoVO.class);

		//User createUser = UserCache.getUser(custom_open_cache_info.getCreateUser());
		//User updateUser = UserCache.getUser(custom_open_cache_info.getUpdateUser());
		//custom_open_cache_infoVO.setCreateUserName(createUser.getName());
		//custom_open_cache_infoVO.setUpdateUserName(updateUser.getName());

		return custom_open_cache_infoVO;
	}

}
