package com.minigod.zero.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.system.dto.TenantSettingDTO;
import com.minigod.zero.system.entity.ZeroDeptSetting;
import com.minigod.zero.system.entity.ZeroPostSetting;
import com.minigod.zero.system.entity.ZeroRoleSetting;
import com.minigod.zero.system.entity.ZeroUserSetting;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/9/27 14:15
 * @description：
 */
public interface TenantSettingService {

	R submit(TenantSettingDTO tenantSetting);

	R initDetail(String tenantId);

	R  generateTenantId();

	R getAllUserAccount();

	R getAllRole();

	R getAllDept();

	R getAllPost();

	R roleSettingSave(ZeroRoleSetting zeroRoleSetting);

	R roleSettingEdit(ZeroRoleSetting zeroRoleSetting);

	R roleQueryPage(IPage page,String keyword);

	R roleSettingDetail(Integer roleSettingId);

	R roleSettingDeleted(Integer roleSettingId);


	R userSettingSave(ZeroUserSetting zeroUserSetting);

	R userSettingEdit(ZeroUserSetting zeroUserSetting);

	R userSettingQueryPage(IPage page,String keyword);

	R userSettingDetail(Integer userSettingId);

	R userSettingDeleted(Integer userSettingId);


	R postSettingSave(ZeroPostSetting zeroPostSetting);

	R postSettingEdit(ZeroPostSetting zeroPostSetting);

	R postSettingQueryPage(IPage page,String keyword);

	R postSettingDetail(Integer postSettingId);

	R postSettingDeleted(Integer postSettingId);


	R deptSettingSave(ZeroDeptSetting zeroDeptSetting);

	R deptSettingEdit(ZeroDeptSetting zeroDeptSetting);

	R deptSettingQueryPage(IPage page,String keyword);

	R deptSettingDetail(Integer deptSettingId);

	R deptSettingDeleted(Integer deptSettingId);

}
