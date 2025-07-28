package com.minigod.zero.trade.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minigod.zero.core.mp.base.AppServiceImpl;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.trade.entity.CustStrategyOrderEntity;
import com.minigod.zero.trade.mapper.CustStrategyOrderMapper;
import com.minigod.zero.trade.service.ICustStrategyOrderService;
import com.minigod.zero.trade.vo.strategy.CustStrategyOrderVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author:yanghu.luo
 * @create: 2023-08-02 14:06
 * @Description: 条件单服务
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class CustStrategyOrderServiceImpl extends AppServiceImpl<CustStrategyOrderMapper, CustStrategyOrderEntity> implements ICustStrategyOrderService {

	private final CustStrategyOrderMapper custStrategyOrderMapper;

	@Override
	public R<String> placeStrategyOrder(CustStrategyOrderVO request) {
		Long id = custStrategyOrderMapper.getMaxId();
		if(id == null){
			id = 0L;
		}
		CustStrategyOrderEntity custStrategyOrder = new CustStrategyOrderEntity();
		custStrategyOrder.setId(++id);
		custStrategyOrder.setCustId(AuthUtil.getTenantCustId());
		custStrategyOrder.setCapitalAccount(request.getCapitalAccount());
		custStrategyOrder.setTradeAccount(request.getTradeAccount());
		custStrategyOrder.setAssetId(request.getAssetId());
		custStrategyOrder.setStockName(request.getStockName());
		custStrategyOrder.setEntrustAmount(request.getEntrustAmount());
		custStrategyOrder.setEntrustPrice(request.getEntrustPrice());
		custStrategyOrder.setEntrustProp(request.getEntrustProp());
		custStrategyOrder.setEntrustBs(request.getEntrustBs());
		custStrategyOrder.setSessionType(request.getSessionType());
		custStrategyOrder.setStrategyPrice(request.getStrategyPrice());
		custStrategyOrder.setStrategyAction(request.getStrategyAction());
		custStrategyOrder.setDeadlineDate(request.getDeadlineDate());
		custStrategyOrder.setDiscType(request.getDiscType());
		custStrategyOrder.setEntrustStatus("C0");
		custStrategyOrder.setExchangeType(request.getExchangeType());
		baseMapper.insert(custStrategyOrder);
		return R.data(custStrategyOrder.getId().toString());
	}

	@Override
	public R errorStrategyOrder(Long id, String errorMessage, boolean delete) {
		CustStrategyOrderEntity custStrategyOrder = new CustStrategyOrderEntity();
		custStrategyOrder.setId(id);
		custStrategyOrder.setEntrustStatus("C4");
		custStrategyOrder.setErrorMessage(errorMessage);
		baseMapper.updateById(custStrategyOrder);
		// 如果下单就失败，该记录不用显示
		if(delete){
			baseMapper.deleteById(id);
		}
		return R.success();
	}

	@Override
	public R updateStrategyOrderExpirationTime(Long id, Long expirationTime) {
		CustStrategyOrderEntity custStrategyOrder = new CustStrategyOrderEntity();
		custStrategyOrder.setId(id);
		custStrategyOrder.setExpirationTime(new Date(expirationTime));
		custStrategyOrder.setUpdateTime(new Date());
		baseMapper.updateById(custStrategyOrder);
		return R.success();
	}

	@Override
	public R updateStrategyOrder(CustStrategyOrderVO request) {
		CustStrategyOrderEntity custStrategyOrder = new CustStrategyOrderEntity();


		CustStrategyOrderEntity strategyOrder = baseMapper.selectOne(Wrappers.<CustStrategyOrderEntity>lambdaQuery()
			.eq(CustStrategyOrderEntity::getAssetId, request.getAssetId())
			.eq(CustStrategyOrderEntity::getId, request.getOrderNo()));
		if(null == strategyOrder){
			return R.fail("订单不存在");
		}
		String entrustStatus = request.getEntrustStatus();
		if("C3".equals(entrustStatus)){
			if(null != request.getEntrustTime()){
				strategyOrder.setEntrustTime(new Date());
				baseMapper.updateById(strategyOrder);
			}
			return R.data("C3");
		}

		custStrategyOrder.setId(Long.valueOf(request.getOrderNo()));
		custStrategyOrder.setStrategyPrice(request.getStrategyPrice());
		custStrategyOrder.setEntrustAmount(request.getEntrustAmount());
		custStrategyOrder.setUpdateTime(new Date());
		baseMapper.updateById(custStrategyOrder);
		return R.success();
	}

	@Override
	public R cancelStrategyOrder(CustStrategyOrderVO request) {
		CustStrategyOrderEntity strategyOrder = baseMapper.selectOne(Wrappers.<CustStrategyOrderEntity>lambdaQuery().
			eq(CustStrategyOrderEntity::getCustId, AuthUtil.getTenantCustId())
			.eq(CustStrategyOrderEntity::getAssetId, request.getAssetId())
			.eq(CustStrategyOrderEntity::getId, request.getOrderNo()));

		if(strategyOrder == null){
            return R.fail("订单不存在");
		}else{
			if(!"C0".equals(strategyOrder.getEntrustStatus())){
				return R.fail("订单状态不允许取消");
			}else if("C3".equals(strategyOrder.getEntrustStatus())){
				return R.data("C3");
			}
			// id编号是唯一的，如果通过entrustNo=id，能查到数据，有且仅有这条id
			CustStrategyOrderEntity custStrategyOrder = new CustStrategyOrderEntity();
			custStrategyOrder.setId(Long.valueOf(request.getOrderNo()));
			custStrategyOrder.setEntrustStatus("C1");
			custStrategyOrder.setUpdateTime(new Date());
			baseMapper.updateById(custStrategyOrder);
		}
		return R.success();
	}

	@Override
	public R<CustStrategyOrderEntity> triggerStrategyOrder(CustStrategyOrderVO request) {
		CustStrategyOrderEntity strategyOrder = null;
		// 条件单触发，查询数据返回
		if(request.getEntrustStatus().equals("C3")){
			strategyOrder = baseMapper.selectOne(Wrappers.<CustStrategyOrderEntity>lambdaQuery()
				.eq(CustStrategyOrderEntity::getId, Long.valueOf(request.getOrderNo()))
				.eq(CustStrategyOrderEntity::getEntrustStatus, "C0"));
		}

		CustStrategyOrderEntity custStrategyOrder = new CustStrategyOrderEntity();
		custStrategyOrder.setId(Long.valueOf(request.getOrderNo()));
		custStrategyOrder.setEntrustStatus(request.getEntrustStatus());
		custStrategyOrder.setUpdateTime(new Date());
		baseMapper.updateById(custStrategyOrder);

		// 条件单触发，查询数据返回
		if(request.getEntrustStatus().equals("C3")){
			if(strategyOrder == null){
				return R.fail();
			}else{
				return R.data(strategyOrder);
			}
		}
		return R.success();
	}
}
