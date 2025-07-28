package com.minigod.zero.customer.back.controller;

import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.customer.back.service.ManualDepositService;
import com.minigod.zero.customer.entity.ManualDepositRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/7/17 10:37
 * @description：手工入金
 */
@RestController
@RequestMapping("/back/deposit")
public class ManualDepositController {

	@Autowired
	private ManualDepositService manualDepositService;

	@GetMapping("/record")
	public R queryList(Query query, String keyword, String startTime, String endTime){
		return manualDepositService.queryList(Condition.getPage(query),keyword,startTime,endTime);
	}

	@PostMapping("/record")
	public R addDepositRecord(@RequestBody ManualDepositRecord manualDepositRecord){
		return manualDepositService.addDepositRecord(manualDepositRecord);
	}

	@GetMapping("/customer/{accountId}")
	public R queryDepositCustomer(@PathVariable("accountId") String accountId){
		return manualDepositService.queryDepositCustomer(accountId);
	}

}
