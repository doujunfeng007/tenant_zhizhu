
package com.minigod.zero.system.wrapper;

import com.minigod.zero.system.cache.DictCache;
import com.minigod.zero.system.entity.Dict;
import com.minigod.zero.system.vo.DictVO;
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
public class DictWrapper extends BaseEntityWrapper<Dict, DictVO> {

	public static DictWrapper build() {
		return new DictWrapper();
	}

	@Override
	public DictVO entityVO(Dict dict) {
		DictVO dictVO = Objects.requireNonNull(BeanUtil.copy(dict, DictVO.class));
		if (Func.equals(dict.getParentId(), ZeroConstant.TOP_PARENT_ID)) {
			dictVO.setParentName(ZeroConstant.TOP_PARENT_NAME);
		} else {
			Dict parent = DictCache.getById(dict.getParentId());
			dictVO.setParentName(parent.getDictValue());
		}
		return dictVO;
	}

	public List<DictVO> listNodeVO(List<Dict> list) {
		List<DictVO> collect = list.stream().map(dict -> BeanUtil.copy(dict, DictVO.class)).collect(Collectors.toList());
		return ForestNodeMerger.merge(collect);
	}

}
