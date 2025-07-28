package com.minigod.zero.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.tool.beans.StkInfo;
import com.minigod.zero.manage.entity.HotRecommendEntity;
import com.minigod.zero.manage.mapper.HotRecommendMapper;
import com.minigod.zero.manage.service.IHotRecommendService;
import com.minigod.zero.manage.vo.HotRecommendVO;
import com.minigod.zero.manage.vo.request.HotRecommendReqVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 热门推荐股票 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-03-18
 */
@Service
public class HotRecommendServiceImpl extends BaseServiceImpl<HotRecommendMapper, HotRecommendEntity> implements IHotRecommendService {

	@Resource
	private ZeroRedis zeroRedis;

	@Override
	public IPage<HotRecommendVO> selectHotRecommendPage(IPage<HotRecommendVO> page, HotRecommendVO mktHotRecommend) {
		return page.setRecords(baseMapper.selectHotRecommendPage(page, mktHotRecommend));
	}

	@Override
	public Long findHotRecommendUpdateVersion(Long paramVersion) {
		LambdaQueryWrapper<HotRecommendEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.gt(HotRecommendEntity::getHotRmVersion, paramVersion);
		return baseMapper.selectCount(queryWrapper);
	}

	@Override
	public List<StkInfo> findRandHotList(HotRecommendReqVO vo) {
		List<HotRecommendEntity> list = baseMapper.findRandHotList(vo.getParams().getCount());
		if(null != list && list.size() > 0){
			List<String> assetIds = list.stream().map(HotRecommendEntity::getAssetId).collect(Collectors.toList());
			List<StkInfo> stks = zeroRedis.protoBatchGet(StkInfo.class,assetIds.toArray(new String[0]));
			return stks;
		}
		return new ArrayList<>();
	}

}
