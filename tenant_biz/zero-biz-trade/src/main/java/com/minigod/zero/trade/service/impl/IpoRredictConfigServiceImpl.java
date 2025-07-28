package com.minigod.zero.trade.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.biz.common.enums.CommonEnums;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.trade.entity.IpoPredictConfigEntity;
import com.minigod.zero.trade.mapper.IpoPredictConfigMapper;
import com.minigod.zero.trade.service.IIpoPredictConfigService;
import com.minigod.zero.trade.vo.IpoPredictConfigVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 新股预约ipo配置 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-01-16
 */
@Slf4j
@Service
public class IpoRredictConfigServiceImpl extends ServiceImpl<IpoPredictConfigMapper, IpoPredictConfigEntity> implements IIpoPredictConfigService {

	@Resource
	private ZeroRedis zeroRedis;

	@Override
	public IPage<IpoPredictConfigVO> selectIpoRredictConfigPage(IPage<IpoPredictConfigVO> page, IpoPredictConfigVO ipoRredictConfig) {
		return page.setRecords(baseMapper.selectIpoRredictConfigPage(page, ipoRredictConfig));
	}

	@Override
	public List<IpoPredictConfigEntity> getPredictIpoConfig(IpoPredictConfigEntity ipoPredictConfig) {
		LambdaQueryWrapper<IpoPredictConfigEntity> queryWrapper = new LambdaQueryWrapper<>();
		if(null != ipoPredictConfig.getId()){
			queryWrapper.eq(IpoPredictConfigEntity::getId, ipoPredictConfig.getId());
		}
		queryWrapper.eq(IpoPredictConfigEntity::getEnableStatus, ipoPredictConfig.getEnableStatus());
		if(null != ipoPredictConfig.getEndPredictTime()){
			queryWrapper.lt(IpoPredictConfigEntity::getEndPredictTime, ipoPredictConfig.getEndPredictTime());
		}
		return baseMapper.selectList(queryWrapper);
	}

	@Override
	public IpoPredictConfigEntity getPredictIpoConfigById(Long predictIpoConfigId, Boolean aTrue) {
		IpoPredictConfigEntity ipoPredictConfig = null;
		try {
			String key = CommonEnums.PRE_IPO_CONFIG + predictIpoConfigId;
			if(aTrue){
				ipoPredictConfig = zeroRedis.protoGet(key,IpoPredictConfigEntity.class);
				if(null != ipoPredictConfig){
					return ipoPredictConfig;
				}
			}
			return baseMapper.selectById(predictIpoConfigId);
		} catch (Exception e) {
			log.error("getPredictIpoConfigById error :" + e.getMessage());
		}
		return null;
	}
}
