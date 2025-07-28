package com.minigod.zero.customer.inside;

import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.customer.api.service.AppCustomerInfoService;
import com.minigod.zero.customer.dto.CustomerInfoDTO;
import com.minigod.zero.customer.entity.CustomerInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 内部调用的相关接口
 */
@RestController
@RequestMapping(AppConstant.PROXY_API_PREFIX)
public class CustomerProxyController {


	@Autowired
	private AppCustomerInfoService customerInfoService;

	/**
	 * 查询客户详情
	 * @param custId
	 * @return
	 */
	@GetMapping("/detail")
	public R selectCustomerDetail(Long custId){
		return customerInfoService.selectProxyCustomerDetail(custId);
	}

	@PostMapping("/acct_check_pwd")
	public R acctCheckPwd(@RequestBody Map params){
		String tradeAccount =params.get("tradeAccount").toString();
		String password =params.get("password").toString();
		return customerInfoService.acctCheckPwd(tradeAccount,password);
	}

	/**
	 * Saas同步租户客户信息（手机号）
	 * @param customerInfoDTO
	 * @return
	 */
	@PostMapping(value = "/update")
	public R sync(@RequestBody CustomerInfoDTO customerInfoDTO) {
		CustomerInfoEntity customerInfo = customerInfoService.updateCustomerInfo(customerInfoDTO);
		return R.data(customerInfo);
	}


}
