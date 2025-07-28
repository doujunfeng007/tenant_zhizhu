
package com.minigod.zero.system.wrapper;

import com.minigod.zero.system.cache.DictCache;
import com.minigod.zero.system.cache.SysCache;
import com.minigod.zero.system.enums.DictEnum;
import com.minigod.zero.system.vo.DeptVO;
import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.constant.ZeroConstant;
import com.minigod.zero.core.tool.node.ForestNodeMerger;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.core.tool.utils.Func;
import com.minigod.zero.system.entity.Dept;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author Chill
 */
public class DeptWrapper extends BaseEntityWrapper<Dept, DeptVO> {

	public static DeptWrapper build() {
		return new DeptWrapper();
	}

	@Override
	public DeptVO entityVO(Dept dept) {
		DeptVO deptVO = Objects.requireNonNull(BeanUtil.copy(dept, DeptVO.class));
		if (Func.equals(dept.getParentId(), ZeroConstant.TOP_PARENT_ID)) {
			deptVO.setParentName(ZeroConstant.TOP_PARENT_NAME);
		} else {
			Dept parent = SysCache.getDept(dept.getParentId());
			deptVO.setParentName(parent.getDeptName());
		}
		String category = DictCache.getValue(DictEnum.ORG_CATEGORY, dept.getDeptCategory());
		deptVO.setDeptCategoryName(category);
		return deptVO;
	}


	public List<DeptVO> listNodeVO(List<Dept> list) {
		List<DeptVO> collect = list.stream().map(dept -> {
			DeptVO deptVO = BeanUtil.copy(dept, DeptVO.class);
			String category = DictCache.getValue(DictEnum.ORG_CATEGORY, dept.getDeptCategory());
			Objects.requireNonNull(deptVO).setDeptCategoryName(category);
			return deptVO;
		}).collect(Collectors.toList());
		return ForestNodeMerger.merge(collect);
	}

	public List<DeptVO> listNodeLazyVO(List<DeptVO> list) {
		List<DeptVO> collect = list.stream().peek(dept -> {
			String category = DictCache.getValue(DictEnum.ORG_CATEGORY, dept.getDeptCategory());
			Objects.requireNonNull(dept).setDeptCategoryName(category);
		}).collect(Collectors.toList());
		return ForestNodeMerger.merge(collect);
	}

}
