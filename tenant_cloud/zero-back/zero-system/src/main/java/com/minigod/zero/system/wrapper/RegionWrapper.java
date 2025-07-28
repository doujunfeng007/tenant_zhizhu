
package com.minigod.zero.system.wrapper;

import com.minigod.zero.system.cache.RegionCache;
import com.minigod.zero.system.entity.Region;
import com.minigod.zero.system.vo.RegionVO;
import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.node.ForestNodeMerger;
import com.minigod.zero.core.tool.utils.BeanUtil;

import java.util.List;
import java.util.Objects;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author Chill
 */
public class RegionWrapper extends BaseEntityWrapper<Region, RegionVO> {

	public static RegionWrapper build() {
		return new RegionWrapper();
	}

	@Override
	public RegionVO entityVO(Region region) {
		RegionVO regionVO = Objects.requireNonNull(BeanUtil.copy(region, RegionVO.class));
		Region parentRegion = RegionCache.getByCode(region.getParentCode());
		regionVO.setParentName(parentRegion.getName());
		return regionVO;
	}

	public List<RegionVO> listNodeLazyVO(List<RegionVO> list) {
		return ForestNodeMerger.merge(list);
	}

}
