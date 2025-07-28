package com.minigod.zero.customer.back.controller;

import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.customer.back.service.CustomerWhiteService;
import com.minigod.zero.customer.dto.CustomerWhiteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/8/2 18:47
 * @description：
 */
@RestController
@RequestMapping("/white_list")
public class CustomerWhiteController {
	@Autowired
	private CustomerWhiteService customerWhiteService;

	/**
	 * 添加白名单
	 * @param customerWhite
	 * @return
	 */
	@PostMapping
	R addWhiteList(@RequestBody CustomerWhiteDTO customerWhite){
		return customerWhiteService.addWhiteList(customerWhite.getCustId());
	}

	/**
	 * 修改状态
	 * @return
	 */
	@PutMapping
	R updateStatus(@RequestBody CustomerWhiteDTO customerWhite){
		return customerWhiteService.updateStatus(customerWhite.getCustId(), customerWhite.getStatus());
	}

	/**
	 * 分页列表
	 * @param keyword
	 * @return
	 */
	@GetMapping
	R queryPage(Query query, String keyword){
		return customerWhiteService.queryPage(Condition.getPage(query),keyword);
	}
}
