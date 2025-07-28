package com.minigod.zero.manage.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.manage.entity.ConfigPageFunctionEntity;
import com.minigod.zero.manage.vo.ConfigPageFunctionVO;

import java.util.Objects;

/**
 * 配置页面组件 包装类,返回视图层所需的字段
 *
 * @author 掌上智珠
 * @since 2023-03-21
 */
public class ConfigPageFunctionWrapper extends BaseEntityWrapper<ConfigPageFunctionEntity, ConfigPageFunctionVO>  {

	public static ConfigPageFunctionWrapper build() {
		return new ConfigPageFunctionWrapper();
 	}

	@Override
	public ConfigPageFunctionVO entityVO(ConfigPageFunctionEntity ConfigPageFunction) {
	    ConfigPageFunctionVO ConfigPageFunctionVO = new ConfigPageFunctionVO();
    	if (ConfigPageFunction != null) {
		    ConfigPageFunctionVO = Objects.requireNonNull(BeanUtil.copy(ConfigPageFunction, ConfigPageFunctionVO.class));

		    //User createUser = UserCache.getUser(ConfigPageFunction.getCreateUser());
		    //User updateUser = UserCache.getUser(ConfigPageFunction.getUpdateUser());
		    //ConfigPageFunctionVO.setCreateUserName(createUser.getName());
		    //ConfigPageFunctionVO.setUpdateUserName(updateUser.getName());
        }
		return ConfigPageFunctionVO;
	}


}
