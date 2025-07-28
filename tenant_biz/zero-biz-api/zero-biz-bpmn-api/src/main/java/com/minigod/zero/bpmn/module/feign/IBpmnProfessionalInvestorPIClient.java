package com.minigod.zero.bpmn.module.feign;

import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * @ClassName: IBpmnProfessionalInvestorPIClient
 * @Description: 专业投资者PI认证模块
 * @Author eric
 * @Date 2024-05-10
 * @Version 1.0
 */
@FeignClient(value = AppConstant.SERVICE_BIZ_BPMN_NAME)
public interface IBpmnProfessionalInvestorPIClient {
	String API_PREFIX = AppConstant.FEIGN_API_PREFIX;
	String PI_FLOW_CALLBACK = API_PREFIX + "/pi_flow_callback";

	/**
	 * 定时器任务回调该方法更改客户级别为PI
	 *
	 * @return
	 */
	@PutMapping(PI_FLOW_CALLBACK)
	R piFlowCallBack();
}
