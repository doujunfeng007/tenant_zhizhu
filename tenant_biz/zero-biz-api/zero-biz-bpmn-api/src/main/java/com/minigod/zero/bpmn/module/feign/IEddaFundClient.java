package com.minigod.zero.bpmn.module.feign;

import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
 * @ClassName: com.minigod.zero.bpmn.module.feign.IEddaFundClient
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/5/17 15:50
 * @Version: 1.0
 */
@FeignClient(
	value = AppConstant.SERVICE_BIZ_BPMN_NAME
)
public interface IEddaFundClient {
	String API_PREFIX = AppConstant.FEIGN_API_PREFIX + "/edda";
	//    String DEPOSIT_NOTIFY = API_PREFIX + "/depositNotify";
	String FUND_PAYMENT_TASK = API_PREFIX + "/fundPaymentTask";
	String INFO_BIND_TASK = API_PREFIX + "/infoBindTask";

	/**
	 * 定时扫描 入金申请调用银行接口
	 *
	 * @return
	 */
	@PostMapping(FUND_PAYMENT_TASK)
	R dbsEddaFundPaymentTask();

	/**
	 * 授权申请
	 * @return
	 */
	@PostMapping(INFO_BIND_TASK)
	R dbsEddaInfoBindTask();
}
