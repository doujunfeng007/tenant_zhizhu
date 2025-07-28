package com.minigod.zero.bpmn.module.feign;

import com.minigod.zero.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 衍生品评估
 *
 * @author eric
 * @since 2024-06-21 17:16:01
 */
@FeignClient(value = "minigod-customer")
public interface IDerivativeAssessmentClient {
	String UPDATE_CUSTOMER_DERIVATIVE = "/customer/derivative";

	/**
	 * 更新衍生品评估结果值
	 *
	 * @param custId
	 * @return
	 */
	@PutMapping(UPDATE_CUSTOMER_DERIVATIVE)
	R updateCustomerDerivative(@RequestParam("custId") Long custId);
}
