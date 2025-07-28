package com.minigod.zero.bpmn.module.feign;

import com.minigod.zero.bpmn.module.account.vo.BackCustomerDetailVO;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 机构开户资料回传给账户中心
 *
 * @author eric
 * @since 2024-05-31 17:43:15
 */
@FeignClient(value = AppConstant.SERVICE_BIZ_BPMN_NAME)
public interface IAccountOpenInfoClient {

	String ACCOUNT_OPEN_INFO = "/account/accountOpenInfo";
	/**
	 * 获取开户详细信息
	 *
	 * @return
	 */
	@GetMapping(ACCOUNT_OPEN_INFO + "/deail")
	R<BackCustomerDetailVO> openAccountUserDetail(@RequestParam(value = "userId")Long userId);

}
