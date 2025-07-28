package com.minigod.zero.bpmn.module.exchange.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.bpmn.module.exchange.entity.CustomerCurrencyExchangeApplication;
import com.minigod.zero.bpmn.module.exchange.vo.CurrencyExchangeApplicationVO;
import com.minigod.zero.bpmn.module.exchange.vo.req.CurrencyExchangeQuery;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author chen
 * @ClassName CustomerCurrencyExchangeApplicationService.java
 * @Description TODO
 * @createTime 2024年03月15日 18:50:00
 */
public interface ICustomerCurrencyExchangeApplicationService extends BaseService<CustomerCurrencyExchangeApplication> {

	IPage<CurrencyExchangeApplicationVO> queryPageList(CurrencyExchangeQuery query, Query pageQuery);

	void exportCheckList(CurrencyExchangeQuery query, Query pageQuery, HttpServletResponse response);

	/**
	 * 根据预约号查询详情
	 *
	 * @param applicationId
	 * @return
	 */
	CustomerCurrencyExchangeApplication queryDetailByApplication(String applicationId);

	String batchClaim(Integer applicationStatus, List<String> asList);

	void batchUnClaim(List<String> asList);

	void rejectNode(String applicationId, String taskId, String reason, Integer status);

	void passNode(String applicationId, String taskId, String reason);

	/**
	 * 挂起
	 *
	 * @param applicationId
	 */
	void hangUp(String applicationId);

	void unHangUp(String applicationId);

	void doProcessingComplete(String applicationId, String instanceId);

	R cancel(String applicationId);

	/**
	 * 撤销失败
	 *
	 * @param applicationId
	 */
	void cancelFail(String applicationId);

	void rejectPreNode(String applicationId, String taskId, String reason);
}
