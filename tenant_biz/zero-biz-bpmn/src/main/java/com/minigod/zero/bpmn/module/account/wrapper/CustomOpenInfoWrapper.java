package com.minigod.zero.bpmn.module.account.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.bpmn.module.account.entity.CustomOpenInfoEntity;
import com.minigod.zero.bpmn.module.account.vo.CustomOpenInfoVO;

/**
 *  包装类,返回视图层所需的字段
 *
 * @author Chill
 */
public class CustomOpenInfoWrapper extends BaseEntityWrapper<CustomOpenInfoEntity, CustomOpenInfoVO>  {

	public static CustomOpenInfoWrapper build() {
		return new CustomOpenInfoWrapper();
 	}

	@Override
	public CustomOpenInfoVO entityVO(CustomOpenInfoEntity custom_open_info) {
		CustomOpenInfoVO custom_open_infoVO = BeanUtil.copy(custom_open_info, CustomOpenInfoVO.class);

		//User createUser = UserCache.getUser(custom_open_info.getCreateUser());
		//User updateUser = UserCache.getUser(custom_open_info.getUpdateUser());
		//custom_open_infoVO.setCreateUserName(createUser.getName());
		//custom_open_infoVO.setUpdateUserName(updateUser.getName());

		return custom_open_infoVO;
	}

}
