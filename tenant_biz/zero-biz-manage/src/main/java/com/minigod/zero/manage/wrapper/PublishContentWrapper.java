package com.minigod.zero.manage.wrapper;

import com.minigod.zero.manage.entity.PublishContentEntity;
import com.minigod.zero.manage.vo.PublishContentVO;
import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;

import java.util.Objects;

/**
 * 帮助中心目录配置表 包装类,返回视图层所需的字段
 *
 * @author 掌上智珠
 * @since 2023-03-18
 */
public class PublishContentWrapper extends BaseEntityWrapper<PublishContentEntity, PublishContentVO>  {

	public static PublishContentWrapper build() {
		return new PublishContentWrapper();
 	}

	@Override
	public PublishContentVO entityVO(PublishContentEntity publishContentEntity) {
	    PublishContentVO publishContentVO = new PublishContentVO();
    	if (publishContentEntity != null) {
			publishContentVO = Objects.requireNonNull(BeanUtil.copy(publishContentEntity, PublishContentVO.class));

		    //User createUser = UserCache.getUser(boardInfo.getCreateUser());
		    //User updateUser = UserCache.getUser(boardInfo.getUpdateUser());
		    //boardInfoVO.setCreateUserName(createUser.getName());
		    //boardInfoVO.setUpdateUserName(updateUser.getName());
        }
		return publishContentVO;
	}


}
