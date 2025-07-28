package com.minigod.zero.trade.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.trade.entity.IpoApplyDataEntity;
import com.minigod.zero.trade.mapper.IpoApplyDataMapper;
import com.minigod.zero.trade.service.IIpoApplyDataService;
import com.minigod.zero.trade.vo.IpoApplyDataVO;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * IPO认购记录 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-02-07
 */
@Service
public class IpoApplyDataServiceImpl extends ServiceImpl<IpoApplyDataMapper, IpoApplyDataEntity> implements IIpoApplyDataService {

	@Override
	public IPage<IpoApplyDataVO> selectIpoApplyDataPage(IPage<IpoApplyDataVO> page, IpoApplyDataVO ipoApplyData) {
		return page.setRecords(baseMapper.selectIpoApplyDataPage(page, ipoApplyData));
	}

	@Override
	public List<IpoApplyDataEntity> findIpoApplyList(String tradeAccount, Date searchBeginDate, Date searchEndDate, String[] statusArray) {
		LambdaQueryWrapper<IpoApplyDataEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(IpoApplyDataEntity::getTradeAccount,tradeAccount);
		queryWrapper.ge(IpoApplyDataEntity::getApplyDate,searchBeginDate);
		queryWrapper.le(IpoApplyDataEntity::getApplyDate,searchEndDate);
		queryWrapper.in(IpoApplyDataEntity::getApplyStatus,statusArray);
		return baseMapper.selectList(queryWrapper);
	}

	@Override
	public Integer saveIpoApplyData(IpoApplyDataEntity applyData) {
		return baseMapper.insert(applyData);
	}

	@Override
	public Integer updateCancelIpoApplyData(String clientId, String assetId) {
		return baseMapper.updateCancelIpoApplyData(clientId,assetId);
	}

	@Override
	public List<IpoApplyDataEntity> findIpoApplyList(IpoApplyDataEntity search) {
		LambdaQueryWrapper<IpoApplyDataEntity> queryWrapper = new LambdaQueryWrapper<>();
		if(null != search.getCustId()){
			queryWrapper.eq(IpoApplyDataEntity::getCustId,search.getCustId());
		}
		if(null != search.getTradeAccount()){
			queryWrapper.eq(IpoApplyDataEntity::getTradeAccount,search.getTradeAccount());
		}
		if(null != search.getAssetId()){
			queryWrapper.eq(IpoApplyDataEntity::getAssetId,search.getAssetId());
		}
		if(null != search.getApplyStatus()){
			queryWrapper.eq(IpoApplyDataEntity::getApplyStatus,search.getApplyStatus());
		}
		return baseMapper.selectList(queryWrapper);
	}


}
