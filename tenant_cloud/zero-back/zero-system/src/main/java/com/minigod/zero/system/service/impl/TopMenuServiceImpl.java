
package com.minigod.zero.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minigod.zero.core.secure.ZeroUser;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.exception.ZeroException;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.system.entity.TopMenu;
import com.minigod.zero.system.mapper.TopMenuMapper;
import com.minigod.zero.system.mapper.ZeroRoleSettingMapper;
import com.minigod.zero.system.mapper.ZeroUserSettingMapper;
import com.minigod.zero.system.service.ITopMenuSettingService;
import com.minigod.zero.system.entity.TopMenuSetting;
import com.minigod.zero.user.entity.User;
import com.minigod.zero.user.feign.IUserClient;
import lombok.AllArgsConstructor;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.system.service.ITopMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 顶部菜单表 服务实现类
 *
 * @author minigod
 */
@Service
@AllArgsConstructor
public class TopMenuServiceImpl extends BaseServiceImpl<TopMenuMapper, TopMenu> implements ITopMenuService {

	@Autowired
	private ZeroRoleSettingMapper zeroRoleSettingMapper;

	@Autowired
	private IUserClient userClient;

	private final ITopMenuSettingService topMenuSettingService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean grant(@NotEmpty List<Long> topMenuIds, @NotEmpty List<Long> menuIds) {
		// 删除顶部菜单配置的菜单集合
		topMenuSettingService.remove(Wrappers.<TopMenuSetting>update().lambda().in(TopMenuSetting::getTopMenuId, topMenuIds));
		// 组装配置
		List<TopMenuSetting> menuSettings = new ArrayList<>();
		topMenuIds.forEach(topMenuId -> menuIds.forEach(menuId -> {
			TopMenuSetting menuSetting = new TopMenuSetting();
			menuSetting.setTopMenuId(topMenuId);
			menuSetting.setMenuId(menuId);
			menuSettings.add(menuSetting);
		}));
		// 新增配置
		topMenuSettingService.saveBatch(menuSettings);
		return true;
	}


	@Override
	public List<TopMenu> getUserTopMenu(ZeroUser user) {
		R<User> result = userClient.userInfoById(AuthUtil.getUserId());
		if (!result.isSuccess()){
			throw new ZeroException("获取用户信息失败");
		}
		User userInfo = result.getData();
		if (userInfo == null){
			throw new ZeroException("用户信息不存在");
		}
		String roleId = userInfo.getRoleId();
		if (StringUtil.isBlank(roleId)){
			throw new ZeroException("用户未绑定角色");
		}
		List<String> roleIds = Arrays.asList(roleId.split(","));
		return baseMapper.getUserTopMenu(user.getTenantId(),roleIds);
	}

}
