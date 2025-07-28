package com.minigod.zero.bpmn.module.report.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minigod.zero.bpmn.module.report.service.DepositAndWithdrawalFundsService;
import com.minigod.zero.core.tool.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/7/23 13:58
 * @description：
 */
@RestController
@RequestMapping("/report")
public class DepositAndWithdrawalFundsReportController {
	@Autowired
	private DepositAndWithdrawalFundsService depositAndWithdrawalFundsService;

	@GetMapping("/depositAndWithdrawal")
	public R queryPage(Long pageIndex,Long pageSize, String startTime, String endTime, String clientId, Integer currency,
					   Integer depositStatus,Integer withdrawalStatus,String applicationIds,Integer type){

		return R.data(depositAndWithdrawalFundsService.queryPage(new Page(pageIndex,pageSize),startTime,endTime,clientId,
			currency,depositStatus,withdrawalStatus,applicationIds,type));
	}
}
