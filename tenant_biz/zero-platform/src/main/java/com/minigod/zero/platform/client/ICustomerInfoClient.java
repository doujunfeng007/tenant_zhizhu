package com.minigod.zero.platform.client;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.platform.vo.CustomerAccountDetailVO;
import com.minigod.zero.platform.vo.CustomerDeviceInfoVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author：eric
 * @Date：2024/4/26 11:00
 */
@FeignClient(value = "minigod-customer")
public interface ICustomerInfoClient {
	String CUSTOMER_DEVICE_LIST = "/customer/device_list";

	String LOAD_CUSTOMER_BY_CUST_ID = "/customer/detailByCustId";

	String ALL_DEVICE_USER_ID_LIST = "/customer/all_device_user_id_list";

	@GetMapping(CUSTOMER_DEVICE_LIST)
	R<List<CustomerDeviceInfoVO>> customerDeviceList(@RequestParam("userIds") List<Long> userIds);


	@GetMapping(LOAD_CUSTOMER_BY_CUST_ID)
	R<CustomerAccountDetailVO> selectCustomerDetailByCustId(@RequestParam("custId")Long custId);

	@GetMapping(ALL_DEVICE_USER_ID_LIST)
	R getAllUserDeviceIdList();

}
