package com.minigod.zero.manage.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.manage.entity.PublishItemEntity;
import com.minigod.zero.manage.vo.PublishItemVO;

import java.util.Objects;

/**
 * 帮助中心内容发布信息 包装类,返回视图层所需的字段
 *
 * @author 掌上智珠
 * @since 2023-03-18
 */
public class PublishItemWrapper extends BaseEntityWrapper<PublishItemEntity, PublishItemVO>  {

	public static PublishItemWrapper build() {
		return new PublishItemWrapper();
 	}

	@Override
	public PublishItemVO entityVO(PublishItemEntity publishItemEntity) {
	    PublishItemVO publishItemVO = new PublishItemVO();
    	if (publishItemVO != null) {
			publishItemVO = Objects.requireNonNull(BeanUtil.copy(publishItemEntity, PublishItemVO.class));

		    //User createUser = UserCache.getUser(boardInfo.getCreateUser());
		    //User updateUser = UserCache.getUser(boardInfo.getUpdateUser());
		    //boardInfoVO.setCreateUserName(createUser.getName());
		    //boardInfoVO.setUpdateUserName(updateUser.getName());
        }
		return publishItemVO;
	}


}
