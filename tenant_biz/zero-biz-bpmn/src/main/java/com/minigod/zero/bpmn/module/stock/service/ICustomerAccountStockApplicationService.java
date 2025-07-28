package com.minigod.zero.bpmn.module.stock.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.bpmn.module.stock.domain.bo.CustomerAccountStockApplicationQuery;
import com.minigod.zero.bpmn.module.stock.domain.vo.CustomerAccountStockApplicationVO;
import com.minigod.zero.bpmn.module.stock.entity.CustomerAccountStockApplicationEntity;

import java.util.List;

/**
 * 股票增开申请流程表 服务接口
 * @author Administrator
 */
public interface ICustomerAccountStockApplicationService extends IService<CustomerAccountStockApplicationEntity> {
	CustomerAccountStockApplicationVO queryByApplicationId(String applicationId);

	IPage<CustomerAccountStockApplicationVO> selectCustomerAccountStockApplicationPage(IPage<Object> page, CustomerAccountStockApplicationQuery applicationQuery);

	String batchClaim(Integer applicationStatus, List<String> asList);

	String batchUnClaim(List<String> asList);

	void rejectNode(String applicationId, String instanceId, String reason);

	void passNode(String applicationId, String taskId, String reason);
}
