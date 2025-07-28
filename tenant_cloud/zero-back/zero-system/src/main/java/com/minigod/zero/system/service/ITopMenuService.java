
package com.minigod.zero.system.service;

import com.minigod.zero.core.secure.ZeroUser;
import com.minigod.zero.system.entity.TopMenu;
import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.user.entity.User;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 顶部菜单表 服务类
 *
 * @author minigod
 */
public interface ITopMenuService extends BaseService<TopMenu> {

	/**
	 * 顶部菜单配置
	 *
	 * @param topMenuIds 顶部菜单id集合
	 * @param menuIds    菜单id集合
	 * @return 是否成功
	 */
	boolean grant(@NotEmpty List<Long> topMenuIds, @NotEmpty List<Long> menuIds);

	/**
	 * 获取用户菜单权限内头部菜单
	 * @param user
	 * @return
	 */
	List<TopMenu> getUserTopMenu(ZeroUser user);
}
