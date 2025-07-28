package com.minigod.zero.trade.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.biz.common.cache.IpoQueueAmt;
import com.minigod.zero.biz.common.enums.EIpoFinanceQueueStatus;
import com.minigod.zero.core.mp.base.BaseEntity;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.trade.entity.IpoFinanceQueueOrderEntity;
import com.minigod.zero.trade.mapper.IpoFinanceQueueOrderMapper;
import com.minigod.zero.trade.service.IIpoFinanceQueueOrderService;
import com.minigod.zero.trade.utils.MarketUtils;
import com.minigod.zero.trade.vo.IpoFinanceQueueOrderVO;
import com.minigod.zero.trade.vo.IpoQueueInfoVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * IPO融资排队订单表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-02-06
 */
@Service
public class IpoFinanceQueueOrderServiceImpl extends ServiceImpl<IpoFinanceQueueOrderMapper, IpoFinanceQueueOrderEntity> implements IIpoFinanceQueueOrderService {

	@Resource
	private ZeroRedis zeroRedis;

	@Override
	public IPage<IpoFinanceQueueOrderVO> selectIpoFinanceQueueOrderPage(IPage<IpoFinanceQueueOrderVO> page, IpoFinanceQueueOrderVO ipoFinanceQueueOrder) {
		return page.setRecords(baseMapper.selectIpoFinanceQueueOrderPage(page, ipoFinanceQueueOrder));
	}

	@Override
	public List<IpoQueueInfoVO> findQueueOrderInfo() {
		return baseMapper.findQueueOrderInfo();
	}

	@Override
	public List<IpoFinanceQueueOrderEntity> findQueueList(String stockCode) {
		return new LambdaQueryChainWrapper<>(baseMapper)
			.eq(IpoFinanceQueueOrderEntity::getStockCode, stockCode)
			.eq(IpoFinanceQueueOrderEntity::getOrderStatus, EIpoFinanceQueueStatus.STS_1.value)
			.orderByAsc(BaseEntity::getCreateTime)
			.last("limit 5000")
			.list();
	}

	@Override
	public int updateIpoFinance(IpoFinanceQueueOrderEntity ipoFinanceQueueOrder) {
		if (null == ipoFinanceQueueOrder.getCustId() || null == ipoFinanceQueueOrder.getOrderStatus()) {
			return 0;
		}
		//修改订单状态
		if (EIpoFinanceQueueStatus.STS_1.value == ipoFinanceQueueOrder.getOrderStatus()) {
			ipoFinanceQueueOrder.setUniqueId(ipoFinanceQueueOrder.getCustId().toString());
		} else {
			ipoFinanceQueueOrder.setUniqueId(ipoFinanceQueueOrder.getId().toString() + ipoFinanceQueueOrder.getCustId());
		}
		return baseMapper.updateById(ipoFinanceQueueOrder);
	}

	@Override
	public IpoFinanceQueueOrderEntity getIpoFinance(IpoFinanceQueueOrderEntity ipoFinanceQueueOrder) {
		if (null == ipoFinanceQueueOrder || null == ipoFinanceQueueOrder.getCustId() || null == ipoFinanceQueueOrder.getStockCode())
			return null;
		IpoFinanceQueueOrderEntity order = zeroRedis.protoGet(MarketUtils.getSymbol(ipoFinanceQueueOrder.getStockCode()) + ipoFinanceQueueOrder.getCustId(), IpoFinanceQueueOrderEntity.class);
		//此处查缓存，不查db
		//ipoFinanceDao.getIpoFinance(ipoFinanceQueueOrder);
		return order;
	}

	@Override
	public BigDecimal getMaxQueueAmount(String assetId, int orderType, boolean cacheFlag) {
		BigDecimal queueAmount = BigDecimal.ZERO;
		try {
			String key = assetId + orderType;
			IpoQueueAmt vo;
			if (cacheFlag) {
				vo = zeroRedis.protoGet(key, IpoQueueAmt.class);
				if (null != vo) return vo.getAmt();
			}
			Long amount = baseMapper.getMaxQueueAmount(assetId, orderType);
			queueAmount = new BigDecimal(null != amount ? amount : 0);
			if (orderType > 0) {
				vo = new IpoQueueAmt();
				vo.setAmt(queueAmount);
				vo.setAssetId(assetId);
				vo.setTyp(orderType);
				vo.setNowTime(new Date());
				zeroRedis.protoSet(key, vo);
			}
		} catch (Exception e) {
			log.error(" getMaxQueueAmount error :" + e.getMessage());
		}
		return queueAmount;
	}

	@Override
	public IpoFinanceQueueOrderEntity getIpoFinanceDb(IpoFinanceQueueOrderEntity order) {
		LambdaQueryWrapper<IpoFinanceQueueOrderEntity> queryWrapper = new LambdaQueryWrapper();
		if (null == order.getStockCode() || null == order.getFundAccount()) {
			return null;
		}
		if (StringUtils.isNotEmpty(order.getStockCode())) {
			queryWrapper.eq(IpoFinanceQueueOrderEntity::getStockCode, order.getStockCode());
		}
		if (StringUtils.isNotEmpty(order.getFundAccount())) {
			queryWrapper.eq(IpoFinanceQueueOrderEntity::getFundAccount, order.getFundAccount());
		}
		if (null != order.getOrderStatus()) {
			queryWrapper.eq(IpoFinanceQueueOrderEntity::getOrderStatus, order.getOrderStatus());
		}
		return baseMapper.selectOne(queryWrapper);
	}

	@Override
	public IpoFinanceQueueOrderEntity getIpoFinanceQueueOrderById(IpoFinanceQueueOrderEntity order) {
		if (null == order.getId()) return null;
		return baseMapper.selectById(order.getId());
	}

	@Override
	public Integer saveIpoFinance(IpoFinanceQueueOrderEntity ipoFinanceQueueOrder) {
		//新增记录为排队中订单，增加唯一标识
		ipoFinanceQueueOrder.setUniqueId(ipoFinanceQueueOrder.getCustId().toString());
		return baseMapper.insert(ipoFinanceQueueOrder);
	}
}
