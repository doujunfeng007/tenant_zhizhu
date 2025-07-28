package com.minigod.zero.customer.api.controller;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.customer.back.service.CostPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/9/13 19:34
 * @description：
 */
@RestController
@RequestMapping("/open_api/package")
public class CustomerPackageController {
	@Autowired
	private CostPackageService costPackageService;

	@GetMapping("/all")
	public R getAllPackage(){
		return costPackageService.getAllPackage();
	}
}
