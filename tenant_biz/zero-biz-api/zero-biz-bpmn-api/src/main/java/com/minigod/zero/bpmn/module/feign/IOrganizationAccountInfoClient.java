package com.minigod.zero.bpmn.module.feign;

import com.minigod.zero.bpmn.module.feign.dto.OrganizationOpenAccountDTO;
import com.minigod.zero.bpmn.module.feign.vo.CustomerInfoVO;
import com.minigod.zero.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * 机构开户资料回传给账户中心
 *
 * @author eric
 * @since 2024-05-31 17:43:15
 */
@FeignClient(value = "minigod-customer")
public interface IOrganizationAccountInfoClient {
	String ORGANIZATION_OPEN_ACCOUNT = "/customer/organization/open_account";
	String GET_CUSTOMER_INFO_BY_PHONE = "/customer/getCustomer_by_phone";

	/**
	 * 开户提交
	 *
	 * @param organizationOpenAccountDTO
	 * @return
	 */
	@PostMapping(ORGANIZATION_OPEN_ACCOUNT)
	R<Map<String, Object>> openAccount(@RequestBody OrganizationOpenAccountDTO organizationOpenAccountDTO);

	/**
	 * 根据手机号获取客户信息
	 *
	 * @param tenantId
	 * @param phone
	 * @param areaCode
	 * @return
	 */
	@GetMapping(GET_CUSTOMER_INFO_BY_PHONE)
	R<CustomerInfoVO> getCustomerInfoByPhone(@RequestParam("tenantId") String tenantId, @RequestParam("phone") String phone, @RequestParam("areaCode") String areaCode);

}
