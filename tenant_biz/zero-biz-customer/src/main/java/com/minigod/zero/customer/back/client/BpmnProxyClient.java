package com.minigod.zero.customer.back.client;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.customer.vo.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/7/8 18:35
 * @description：
 */
@FeignClient(value = "zero-biz-bpmn")
public interface BpmnProxyClient {
	String QUERY_OPEN_ACCOUNT_USER_LIST = "/back/account/accountOpenInfo/userList";

	String QUERY_OPEN_ACCOUNT_USER_DETAIL = "/back/account/accountOpenInfo/userDetail";

	String QUERY_DEPOSIT_AND_WITHDRAWAL_FUNDS_REPORT = "/report/depositAndWithdrawal";

	String MANUAL_DEPOSIT = "/open/open_api/manual/deposit/submit";


	@GetMapping(QUERY_OPEN_ACCOUNT_USER_LIST)
	R<Page<CustomerOpenAccountVO>> openAccountUserList(@RequestParam("keyword") String keyword, @RequestParam("startTime")String startTime,
													   @RequestParam("endTime") String endTime, @RequestParam("pageIndex")Integer pageIndex,
													   @RequestParam("pageSize") Integer pageSize);

	@GetMapping(QUERY_OPEN_ACCOUNT_USER_DETAIL)
	R<BackCustomerDetailVO> openAccountDetail(@RequestParam("userId") Long userId);


	@GetMapping(QUERY_DEPOSIT_AND_WITHDRAWAL_FUNDS_REPORT)
	R<ReportVO>  queryPage(@RequestParam("pageIndex") Long pageIndex, @RequestParam("pageSize") Long pageSize,
						   @RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime,
						   @RequestParam("clientId") String clientId, @RequestParam("currency") Integer currency,
						   @RequestParam("depositStatus") Integer depositStatus, @RequestParam("withdrawalStatus") Integer withdrawalStatus,
						   @RequestParam("applicationIds") String applicationIds,@RequestParam("type")Integer type);


	@PostMapping(MANUAL_DEPOSIT)
	R manualDeposit(@RequestBody ManualDepositVO manualDepositVO);
}
