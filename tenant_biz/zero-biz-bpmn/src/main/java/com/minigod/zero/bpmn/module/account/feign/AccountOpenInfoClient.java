package com.minigod.zero.bpmn.module.account.feign;

import com.minigod.zero.bpmn.module.account.service.IAccountOpenInfoService;
import com.minigod.zero.bpmn.module.account.vo.BackCustomerDetailVO;
import com.minigod.zero.bpmn.module.feign.IAccountOpenInfoClient;
import com.minigod.zero.core.tool.api.R;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName: com.minigod.zero.bpmn.module.account.feign.AccountOpenInfoClient
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/7/23 19:04
 * @Version: 1.0
 */
@Slf4j
@AllArgsConstructor
@RestController
public class AccountOpenInfoClient implements IAccountOpenInfoClient {
	@Resource
	private IAccountOpenInfoService accountOpenInfoService;

	@GetMapping(ACCOUNT_OPEN_INFO + "/deail")
	public R<BackCustomerDetailVO> openAccountUserDetail(Long userId) {
		return R.data(accountOpenInfoService.openAccountUserDetail(userId));
	}
}
