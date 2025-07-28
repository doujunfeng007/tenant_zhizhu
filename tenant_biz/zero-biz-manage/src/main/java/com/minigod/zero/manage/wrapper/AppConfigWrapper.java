package com.minigod.zero.manage.wrapper;

import com.minigod.zero.manage.entity.AppConfigEntity;
import com.minigod.zero.manage.vo.AppConfigVO;
import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;

import java.util.Objects;

/**
 * APP配置 包装类,返回视图层所需的字段
 *
 * @author 掌上智珠
 * @since 2023-03-23
 */
public class AppConfigWrapper extends BaseEntityWrapper<AppConfigEntity, AppConfigVO>  {

	public static AppConfigWrapper build() {
		return new AppConfigWrapper();
 	}

	@Override
	public AppConfigVO entityVO(AppConfigEntity appConfig) {
	    AppConfigVO appConfigVO = new AppConfigVO();
    	if (appConfig != null) {
		    appConfigVO = Objects.requireNonNull(BeanUtil.copy(appConfig, AppConfigVO.class));

		    //User createUser = UserCache.getUser(appConfig.getCreateUser());
		    //User updateUser = UserCache.getUser(appConfig.getUpdateUser());
		    //appConfigVO.setCreateUserName(createUser.getName());
		    //appConfigVO.setUpdateUserName(updateUser.getName());
        }
		return appConfigVO;
	}


}
