package com.minigod.zero.manage.proxy;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.manage.vo.CustomerAccountVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/8/16 15:32
 * @description：
 */
@FeignClient(value = "minigod-customer")
public interface CustomerProxy {

	@GetMapping("/account_info")
	R<CustomerAccountVO> customerAccountInfo(@RequestParam("custId") Long custId);


	@GetMapping("/customer/email_list")
	R<String> customerEmailList(@RequestParam("userIds") List<Long> userIds);

//	@GetMapping("/customer/selectCustomerByCustIds")
//	R<List<CustomerInfoEntity>> selectCustomerByCustIds(@RequestParam("custIds") List<Long> custIds);
//
//	@GetMapping("/customer/selectCustomerByPhoneOrCustId")
//	R<List<CustomerInfoEntity>> selectCustomerByPhoneOrCustId(@RequestParam("searchCondition") String searchCondition);
}
