package com.minigod.zero.bpmn.module.exchange.service;

import com.minigod.zero.bpmn.module.exchange.entity.CustomerCurrencyExchangeInfo;
import com.minigod.zero.bpmn.module.exchange.vo.CustomerCurrencyExchangeInfoVO;
import com.minigod.zero.core.mp.base.BaseService;

import java.util.List;

/**
 * @ClassName CustomerCurrencyExchangeInfoService.java
 * @author chen
 * @Description TODO
 * @createTime 2024年03月15日 18:50:00
 */
public interface ICustomerCurrencyExchangeInfoService extends BaseService<CustomerCurrencyExchangeInfo> {

	/**
	 * 根据预约号查询详情
	 * @param applicationId
	 * @return
	 */
	CustomerCurrencyExchangeInfoVO queryDetailByApplication(String applicationId);

	CustomerCurrencyExchangeInfo queryInfoByApplication(String applicationId);

    List<CustomerCurrencyExchangeInfoVO> selectList(CustomerCurrencyExchangeInfo customerCurrencyExchangeInfo);
}
