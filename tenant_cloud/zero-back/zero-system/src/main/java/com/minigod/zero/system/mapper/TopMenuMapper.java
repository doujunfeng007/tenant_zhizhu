
package com.minigod.zero.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.system.entity.TopMenu;
import com.minigod.zero.system.entity.TopMenuSetting;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 顶部菜单表 Mapper 接口
 *
 * @author minigod
 */
public interface TopMenuMapper extends BaseMapper<TopMenu> {
	TopMenu selectByMenuId(Long menuId);

	List<TopMenuSetting> selectByMenuIdList(@Param("menuIdList") List<Long> menuIdList);

	List<TopMenu> getUserTopMenu(@Param("tenantId") String tenantId,
								 @Param("roleIds")List<String> roleIds);
}
