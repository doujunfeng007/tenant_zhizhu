package com.minigod.zero.bpmn.module.exchange.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpmn.module.exchange.entity.CurrencyExchangeRateInfo;
import com.minigod.zero.bpmn.module.exchange.vo.req.CurrencyExchangeRateQuery;
import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.core.mp.support.Query;

import java.util.List;

/**
 * @ClassName CurrencyExchangeRateInfoService.java
 * @author chen
 * @Description TODO
 * @createTime 2024年03月15日 18:49:00
 */
public interface ICurrencyExchangeRateInfoService extends BaseService<CurrencyExchangeRateInfo> {

	/**
	 * 分页查询
	 * @param query
	 * @param pageQuery
	 * @return
	 */
	IPage<CurrencyExchangeRateInfo> queryPageList(CurrencyExchangeRateQuery query, Query pageQuery);

	/**
	 * 查询列表
	 * @param query
	 * @return
	 */
	List<CurrencyExchangeRateInfo> queryList(CurrencyExchangeRateQuery query);
}
