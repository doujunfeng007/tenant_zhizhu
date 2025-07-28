package com.minigod.zero.bpmn.module.exchange.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpmn.module.exchange.entity.CurrencyExchangeRateInfo;
import com.minigod.zero.bpmn.module.exchange.mapper.CurrencyExchangeRateInfoMapper;
import com.minigod.zero.bpmn.module.exchange.service.ICurrencyExchangeRateInfoService;
import com.minigod.zero.bpmn.module.exchange.vo.req.CurrencyExchangeRateQuery;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName CurrencyExchangeRateInfoServiceImpl.java
 * @author chen
 * @Description TODO
 * @createTime 2024年03月15日 18:49:00
 */
@Service
public class CurrencyExchangeRateInfoServiceImpl extends BaseServiceImpl<CurrencyExchangeRateInfoMapper, CurrencyExchangeRateInfo> implements ICurrencyExchangeRateInfoService {

	@Override
	public IPage<CurrencyExchangeRateInfo> queryPageList(CurrencyExchangeRateQuery query, Query page) {
		IPage<CurrencyExchangeRateInfo> result =baseMapper.queryPageList(Condition.getPage(page), query);
		return result;
	}

	@Override
	public List<CurrencyExchangeRateInfo> queryList(CurrencyExchangeRateQuery query) {
		return baseMapper.queryList(query);
	}

}
