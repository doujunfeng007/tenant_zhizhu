package com.minigod.zero.manage.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.manage.entity.SysMaintenanceEntity;
import com.minigod.zero.manage.vo.SysMaintenanceVO;

import java.util.Objects;

/**
 * 系统维护 包装类,返回视图层所需的字段
 *
 * @author 掌上智珠
 * @since 2023-03-23
 */
public class SysMaintenanceWrapper extends BaseEntityWrapper<SysMaintenanceEntity, SysMaintenanceVO>  {

	public static SysMaintenanceWrapper build() {
		return new SysMaintenanceWrapper();
 	}

	@Override
	public SysMaintenanceVO entityVO(SysMaintenanceEntity sysMaintenance) {
	    SysMaintenanceVO sysMaintenanceVO = new SysMaintenanceVO();
    	if (sysMaintenance != null) {
		    sysMaintenanceVO = Objects.requireNonNull(BeanUtil.copy(sysMaintenance, SysMaintenanceVO.class));

		    //User createUser = UserCache.getUser(sysMaintenance.getCreateUser());
		    //User updateUser = UserCache.getUser(sysMaintenance.getUpdateUser());
		    //sysMaintenanceVO.setCreateUserName(createUser.getName());
		    //sysMaintenanceVO.setUpdateUserName(updateUser.getName());
        }
		return sysMaintenanceVO;
	}


}
