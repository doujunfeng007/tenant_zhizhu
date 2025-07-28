package com.minigod.zero.bpmn.module.account.feign;

import com.minigod.zero.bpmn.module.account.entity.OrganizationOpenInfoEntity;
import com.minigod.zero.bpmn.module.account.service.IOrganizationOpenAccountOnlineService;
import com.minigod.zero.bpmn.module.feign.IOrganizationAccountOpenInfoClient;
import com.minigod.zero.core.tool.api.R;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: com.minigod.zero.bpmn.module.account.feign.OrganizationAccountOpenInfoClient
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/7/23 15:38
 * @Version: 1.0
 */
@Slf4j
@AllArgsConstructor
@RestController
public class OrganizationAccountOpenInfoClient implements IOrganizationAccountOpenInfoClient {
	private final IOrganizationOpenAccountOnlineService organizationOpenAccountOnlineService;

	@Override
	@GetMapping(ORGANIZATION_OPEN_ACCOUNT + "/deail")
	public R<OrganizationOpenInfoEntity> queryOrganizationOpenAccountById(Long custId) {
		return R.data(organizationOpenAccountOnlineService.queryOrganizationOpenAccountByCustId(custId));

	}
}
