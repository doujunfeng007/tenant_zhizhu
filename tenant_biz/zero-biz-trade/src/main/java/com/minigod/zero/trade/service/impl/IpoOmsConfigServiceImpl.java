package com.minigod.zero.trade.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.trade.entity.IpoOmsConfigEntity;
import com.minigod.zero.trade.mapper.IpoOmsConfigMapper;
import com.minigod.zero.trade.service.IIpoOmsConfigService;
import com.minigod.zero.trade.vo.IpoOmsConfigVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 新股ipo配置 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-01-16
 */
@Service
public class IpoOmsConfigServiceImpl extends ServiceImpl<IpoOmsConfigMapper, IpoOmsConfigEntity> implements IIpoOmsConfigService {

	@Resource
	private ZeroRedis zeroRedis;

	@Override
	public IPage<IpoOmsConfigVO> selectIpoOmsConfigPage(IPage<IpoOmsConfigVO> page, IpoOmsConfigVO ipoOmsConfig) {
		return page.setRecords(baseMapper.selectIpoOmsConfigPage(page, ipoOmsConfig));
	}

	@Override
	public List<String> checkExistOmsConfigIpoAssetId(List<String> assetIds) {
		List<IpoOmsConfigEntity> list = new LambdaQueryChainWrapper<>(baseMapper)
			.select(IpoOmsConfigEntity::getAssetId)
			.in(IpoOmsConfigEntity::getAssetId, assetIds)
			.list();

		return list.stream().map(IpoOmsConfigEntity::getAssetId).collect(Collectors.toList());
	}

	@Override
	public IpoOmsConfigEntity getOmsConfigIpo(String assetId) {
		IpoOmsConfigEntity ipoOmsConfigEntity = zeroRedis.protoGet(assetId,IpoOmsConfigEntity.class);
		if(null == ipoOmsConfigEntity){
			ipoOmsConfigEntity = baseMapper.selectOne(new LambdaQueryWrapper<IpoOmsConfigEntity>().eq(IpoOmsConfigEntity::getAssetId,assetId));
			if(null != ipoOmsConfigEntity){
				zeroRedis.protoSet(assetId,ipoOmsConfigEntity,60 * 3);
			}
		}
		return ipoOmsConfigEntity;
	}

}
