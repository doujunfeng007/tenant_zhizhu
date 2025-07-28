package com.minigod.zero.bpmn.module.feign;

import com.minigod.zero.bpmn.module.account.entity.OrganizationOpenInfoEntity;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 机构开户资料回传给账户中心
 *
 * @author eric
 * @since 2024-05-31 17:43:15
 */
@FeignClient(value = AppConstant.SERVICE_BIZ_BPMN_NAME)
public interface IOrganizationAccountOpenInfoClient {
	String ORGANIZATION_OPEN_ACCOUNT = "/customer/organization-account-open-info";
	/**
	 * 获取机构户申请信息
	 *
	 * @return
	 */
	@GetMapping(ORGANIZATION_OPEN_ACCOUNT + "/deail")
	R<OrganizationOpenInfoEntity> queryOrganizationOpenAccountById(@RequestParam("custId") Long custId) ;

}
