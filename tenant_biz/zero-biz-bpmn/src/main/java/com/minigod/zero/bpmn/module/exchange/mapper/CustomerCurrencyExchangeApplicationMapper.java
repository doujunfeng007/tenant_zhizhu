package com.minigod.zero.bpmn.module.exchange.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpmn.module.exchange.entity.CustomerCurrencyExchangeApplication;
import com.minigod.zero.bpmn.module.exchange.vo.CurrencyExchangeApplicationVO;
import com.minigod.zero.bpmn.module.exchange.vo.req.CurrencyExchangeQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName CustomerCurrencyExchangeApplicationMapper.java
 * @author chen
 * @Description TODO
 * @createTime 2024年03月15日 18:50:00
 */
public interface CustomerCurrencyExchangeApplicationMapper extends BaseMapper<CustomerCurrencyExchangeApplication> {
    IPage<CurrencyExchangeApplicationVO> queryPageList(IPage  page, @Param("query") CurrencyExchangeQuery query);

	List<CurrencyExchangeApplicationVO> queryList(@Param("query") CurrencyExchangeQuery query);

	int addAssignDrafter(@Param("applicationId") String applicationId, @Param("userId") String userId);

	int clearAssignDrafter(@Param("applicationId") String applicationId);

	CustomerCurrencyExchangeApplication queryByApplicationId(@Param("applicationId")String applicationId);
}
