package com.minigod.zero.bpmn.module.exchange.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpmn.module.exchange.entity.CurrencyExchangeRateInfo;
import com.minigod.zero.bpmn.module.exchange.vo.req.CurrencyExchangeRateQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName CurrencyExchangeRateInfoMapper.java
 * @author chen
 * @Description TODO
 * @createTime 2024年03月15日 18:49:00
 */
public interface CurrencyExchangeRateInfoMapper extends BaseMapper<CurrencyExchangeRateInfo> {
	/**
	 * 分页查询
	 * @param page
	 * @param query
	 * @return
	 */
    IPage<CurrencyExchangeRateInfo> queryPageList(IPage  page, @Param("query") CurrencyExchangeRateQuery query);

	List<CurrencyExchangeRateInfo> queryList(@Param("query") CurrencyExchangeRateQuery query);
}
