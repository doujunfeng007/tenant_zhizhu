
package com.minigod.zero.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.system.cache.RegionCache;
import com.minigod.zero.system.entity.Region;
import com.minigod.zero.system.excel.RegionExcel;
import com.minigod.zero.system.mapper.RegionMapper;
import com.minigod.zero.system.vo.RegionVO;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.core.tool.utils.Func;
import com.minigod.zero.core.tool.utils.StringPool;
import com.minigod.zero.system.service.IRegionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 行政区划表 服务实现类
 *
 * @author Chill
 */
@Service
public class RegionServiceImpl extends ServiceImpl<RegionMapper, Region> implements IRegionService {

	@Override
	public boolean submit(Region region) {
		Long cnt = baseMapper.selectCount(Wrappers.<Region>query().lambda().eq(Region::getCode, region.getCode()));
		if (cnt > 0L) {
			return this.updateById(region);
		}
		// 设置祖区划编号
		Region parent = RegionCache.getByCode(region.getParentCode());
		if (Func.isNotEmpty(parent) || Func.isNotEmpty(parent.getCode())) {
			String ancestors = parent.getAncestors() + StringPool.COMMA + parent.getCode();
			region.setAncestors(ancestors);
		}
		// 设置省、市、区、镇、村
		Integer level = region.getRegionLevel();
		String code = region.getCode();
		String name = region.getName();
		if (level == RegionCache.PROVINCE_LEVEL) {
			region.setProvinceCode(code);
			region.setProvinceName(name);
		} else if (level == RegionCache.CITY_LEVEL) {
			region.setCityCode(code);
			region.setCityName(name);
		} else if (level == RegionCache.DISTRICT_LEVEL) {
			region.setDistrictCode(code);
			region.setDistrictName(name);
		} else if (level == RegionCache.TOWN_LEVEL) {
			region.setTownCode(code);
			region.setTownName(name);
		} else if (level == RegionCache.VILLAGE_LEVEL) {
			region.setVillageCode(code);
			region.setVillageName(name);
		}
		return this.save(region);
	}

	@Override
	public boolean removeRegion(String id) {
		Long cnt = baseMapper.selectCount(Wrappers.<Region>query().lambda().eq(Region::getParentCode, id));
		if (cnt > 0L) {
			throw new ServiceException("请先删除子节点!");
		}
		return removeById(id);
	}

	@Override
	public List<RegionVO> lazyList(String parentCode, Map<String, Object> param) {
		return baseMapper.lazyList(parentCode, param);
	}

	@Override
	public List<RegionVO> lazyTree(String parentCode, Map<String, Object> param) {
		return baseMapper.lazyTree(parentCode, param);
	}

	@Override
	public void importRegion(List<RegionExcel> data, Boolean isCovered) {
		List<Region> list = new ArrayList<>();
		data.forEach(regionExcel -> {
			Region region = BeanUtil.copy(regionExcel, Region.class);
			list.add(region);
		});
		if (isCovered) {
			this.saveOrUpdateBatch(list);
		} else {
			this.saveBatch(list);
		}
	}

	@Override
	public List<RegionExcel> exportRegion(Wrapper<Region> queryWrapper) {
		return baseMapper.exportRegion(queryWrapper);
	}
}
