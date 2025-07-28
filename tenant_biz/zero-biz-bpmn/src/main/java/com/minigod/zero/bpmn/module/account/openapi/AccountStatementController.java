package com.minigod.zero.bpmn.module.account.openapi;

import com.minigod.zero.bpmn.module.feign.ICustomerInfoClient;
import com.minigod.zero.bpmn.module.feign.dto.StatementPageDTO;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.log.annotation.ApiLog;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: com.minigod.zero.customer.api.controller.StatementController
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/5/31 15:16
 * @Version: 1.0
 */

@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.OPEN_API_PREFIX + "/open_api/statement")
@Api(value = "结单", tags = "结单")
public class AccountStatementController {

	@Autowired
	private ICustomerInfoClient iCustomerInfoClient;

	/**
	 * 日 月 结单
	 * @return
	 */
	@ApiLog("查询日月结单")
	@PostMapping("/reports")
	public R reports(@RequestBody StatementPageDTO statementPageDTO){
		if (statementPageDTO.getCustId()==null) {
			statementPageDTO.setCustId(AuthUtil.getTenantCustId());
		}
		return iCustomerInfoClient.reports(statementPageDTO);
	}
}
