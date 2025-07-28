package com.minigod.zero.trade.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.biz.common.cache.IpoPredictQueueAmt;
import com.minigod.zero.biz.common.enums.CommonEnums;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.trade.entity.IpoPredictFinanceOrderEntity;
import com.minigod.zero.trade.mapper.IpoPredictFinanceOrderMapper;
import com.minigod.zero.trade.service.IIpoPredictFinanceOrderService;
import com.minigod.zero.trade.vo.IpoPredictFinanceOrderVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 新股预约融资排队订单表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-05-22
 */
@Service
public class IpoPredictFinanceOrderServiceImpl extends ServiceImpl<IpoPredictFinanceOrderMapper, IpoPredictFinanceOrderEntity> implements IIpoPredictFinanceOrderService {

	@Resource
	private ZeroRedis zeroRedis;

	@Override
	public IPage<IpoPredictFinanceOrderVO> selectIpoPredictFinanceOrderPage(IPage<IpoPredictFinanceOrderVO> page, IpoPredictFinanceOrderVO IpoPredictFinanceOrder) {
		return page.setRecords(baseMapper.selectIpoPredictFinanceOrderPage(page, IpoPredictFinanceOrder));
	}

	@Override
	public List<IpoPredictFinanceOrderEntity> getIpoPredictFinanceOrderByUserId(Long predictIpoConfigId, Long custId, Long id) {
		LambdaQueryWrapper<IpoPredictFinanceOrderEntity> queryWrapper = new LambdaQueryWrapper<>();
		if (null != predictIpoConfigId) {
			queryWrapper.eq(IpoPredictFinanceOrderEntity::getPredictConfigId, predictIpoConfigId);
		}
		if (null != custId) {
			queryWrapper.eq(IpoPredictFinanceOrderEntity::getCustId, custId);
		}
		if (null != id) {
			queryWrapper.eq(IpoPredictFinanceOrderEntity::getId, id);
		}
		return baseMapper.selectList(queryWrapper);
	}

	@Override
	public Number getTotalPredictQueueAmount(Long predictIpoConfigId, int orderTpye, Boolean cacheFlag) {
		BigDecimal queueAmount = BigDecimal.ZERO;
		try {
			String key = CommonEnums.PRE_IPO_QUEUE + predictIpoConfigId + orderTpye;
			IpoPredictQueueAmt vo;
			if (cacheFlag) {
				vo = zeroRedis.protoGet(key, IpoPredictQueueAmt.class);
				if (null != vo) return vo.getAmt();
			}
			queueAmount = baseMapper.getMaxPredictQueueAmount(predictIpoConfigId, orderTpye);
			if (orderTpye > 0) {
				vo = new IpoPredictQueueAmt();
				vo.setAmt(queueAmount);
				vo.setPredictConfigId(predictIpoConfigId);
				vo.setTyp(orderTpye);
				vo.setNowTime(new Date());
				zeroRedis.protoSet(key, vo);
			}
		} catch (Exception e) {
			log.error(" getTotalPredictQueueAmount error :" + e.getMessage());
		}
		return queueAmount;
	}

	@Override
	public Integer saveIpoPredictFinanceOrder(IpoPredictFinanceOrderEntity ipoPredictFinanceOrder) {
		return baseMapper.insert(ipoPredictFinanceOrder);
	}

	@Override
	public IpoPredictFinanceOrderEntity getIpoPredictFinanceOrderByPredictIpoConfigId(Long predictIpoConfigId, Long custId, Boolean cachFlag) {
		String key = IpoPredictFinanceOrderEntity.class.getSimpleName() + predictIpoConfigId + custId;
		if (cachFlag) {
			IpoPredictFinanceOrderEntity ipoPredictFinanceOrder = zeroRedis.protoGet(key, IpoPredictFinanceOrderEntity.class);
			if (Objects.nonNull(ipoPredictFinanceOrder)) {
				return ipoPredictFinanceOrder;
			}
		}
		List<IpoPredictFinanceOrderEntity> ipoPredictFinanceOrders = baseMapper.getIpoPredictFinanceOrderByUserId(predictIpoConfigId, custId, null);
		if (ipoPredictFinanceOrders == null) {
			return null;
		}
		List<IpoPredictFinanceOrderEntity> collect = ipoPredictFinanceOrders.stream().sorted(Comparator.comparing(IpoPredictFinanceOrderEntity::getId).reversed()).collect(Collectors.toList());

		if (!collect.isEmpty()) {
			// 把最新的一条同步到缓存 同步到缓存, 一个小时失效
			IpoPredictFinanceOrderEntity ipoPredictFinanceOrder1 = collect.get(0);
			zeroRedis.protoSet(key, ipoPredictFinanceOrder1, 60 * 60);
			return ipoPredictFinanceOrder1;
		}
		return null;
	}

	@Override
	public Integer updateIpoPredictOrderQueueStatus(IpoPredictFinanceOrderEntity ipoPredictFinanceOrder) {
		// 更新数据库
		baseMapper.updateById(ipoPredictFinanceOrder);
		// 删除缓存
		String key = IpoPredictFinanceOrderEntity.class.getSimpleName() + ipoPredictFinanceOrder.getPredictConfigId() + ipoPredictFinanceOrder.getCustId();
		zeroRedis.hDel(IpoPredictFinanceOrderEntity.class, key);
		return 1;
	}

	@Override
	public Integer updateIpoPredictOrderQueueStatusById(IpoPredictFinanceOrderEntity ipoPredictFinanceOrder) {
		IpoPredictFinanceOrderEntity old = baseMapper.selectById(ipoPredictFinanceOrder.getId());
		old.setOrderStatus(ipoPredictFinanceOrder.getOrderStatus());
		old.setAssetId(ipoPredictFinanceOrder.getAssetId());
		old.setUpdateTime(new Date());
		return baseMapper.updateById(old);
	}


}
