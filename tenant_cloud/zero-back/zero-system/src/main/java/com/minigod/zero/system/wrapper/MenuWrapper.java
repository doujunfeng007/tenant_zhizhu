
package com.minigod.zero.system.wrapper;

import com.minigod.zero.system.cache.DictCache;
import com.minigod.zero.system.cache.SysCache;
import com.minigod.zero.system.entity.Menu;
import com.minigod.zero.system.enums.DictEnum;
import com.minigod.zero.system.vo.MenuVO;
import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.constant.ZeroConstant;
import com.minigod.zero.core.tool.node.ForestNodeMerger;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.core.tool.utils.Func;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author Chill
 */
public class MenuWrapper extends BaseEntityWrapper<Menu, MenuVO> {

	public static MenuWrapper build() {
		return new MenuWrapper();
	}

	@Override
	public MenuVO entityVO(Menu menu) {
		MenuVO menuVO = Objects.requireNonNull(BeanUtil.copy(menu, MenuVO.class));
		if (Func.equals(menu.getParentId(), ZeroConstant.TOP_PARENT_ID)) {
			menuVO.setParentName(ZeroConstant.TOP_PARENT_NAME);
		} else {
			Menu parent = SysCache.getMenu(menu.getParentId());
			menuVO.setParentName(parent.getName());
		}
		String category = DictCache.getValue(DictEnum.MENU_CATEGORY, Func.toInt(menuVO.getCategory()));
		String action = DictCache.getValue(DictEnum.BUTTON_FUNC, Func.toInt(menuVO.getAction()));
		String open = DictCache.getValue(DictEnum.YES_NO, Func.toInt(menuVO.getIsOpen()));
		menuVO.setCategoryName(category);
		menuVO.setActionName(action);
		menuVO.setIsOpenName(open);
		return menuVO;
	}

	public List<MenuVO> listNodeVO(List<Menu> list) {
		List<MenuVO> collect = list.stream().map(menu -> BeanUtil.copy(menu, MenuVO.class)).collect(Collectors.toList());
		return ForestNodeMerger.merge(collect);
	}

	public List<MenuVO> listNodeLazyVO(List<MenuVO> list) {
		return ForestNodeMerger.merge(list);
	}

}
