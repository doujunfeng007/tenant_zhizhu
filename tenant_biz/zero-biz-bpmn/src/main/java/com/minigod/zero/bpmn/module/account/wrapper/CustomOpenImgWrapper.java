package com.minigod.zero.bpmn.module.account.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.bpmn.module.account.entity.CustomOpenImgEntity;
import com.minigod.zero.bpmn.module.account.vo.CustomOpenImgVO;

/**
 *  包装类,返回视图层所需的字段
 *
 * @author Chill
 */
public class CustomOpenImgWrapper extends BaseEntityWrapper<CustomOpenImgEntity, CustomOpenImgVO>  {

	public static CustomOpenImgWrapper build() {
		return new CustomOpenImgWrapper();
 	}

	@Override
	public CustomOpenImgVO entityVO(CustomOpenImgEntity custom_open_img) {
		CustomOpenImgVO custom_open_imgVO = BeanUtil.copy(custom_open_img, CustomOpenImgVO.class);

		//User createUser = UserCache.getUser(custom_open_img.getCreateUser());
		//User updateUser = UserCache.getUser(custom_open_img.getUpdateUser());
		//custom_open_imgVO.setCreateUserName(createUser.getName());
		//custom_open_imgVO.setUpdateUserName(updateUser.getName());

		return custom_open_imgVO;
	}

}
