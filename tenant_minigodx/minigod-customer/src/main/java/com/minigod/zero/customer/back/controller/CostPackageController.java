package com.minigod.zero.customer.back.controller;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.customer.back.service.CostPackageService;
import com.minigod.zero.customer.entity.CostPackage;
import com.minigod.zero.customer.entity.CostPackageDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/9/11 21:08
 * @description：
 */
@RestController
@RequestMapping("/back/cost_package")
public class CostPackageController {
	@Autowired
	private CostPackageService costPackageService;

	@GetMapping
	public R costPackage(String number, String packageName,Integer current,Integer size) {
		return costPackageService.costPackage(number, packageName, current, size);
	}

	@PostMapping
	public R saveCostPackage(@RequestBody CostPackage costPackage){
		return costPackageService.saveCostPackage(costPackage);
	}
	@PutMapping
	public R editCostPackage(@RequestBody CostPackage costPackage){
		return costPackageService.editCostPackage(costPackage);
	}

	@GetMapping("/{packageId}")
	public R costPackage(@PathVariable("packageId") Long packageId){
		return costPackageService.costPackage(packageId);
	}

	@DeleteMapping("/detail/{packageDetailId}")
	public R deleteCostPackageDetail(Long packageId,@PathVariable("packageDetailId") Long packageDetailId){
		return costPackageService.deleteCostPackageDetail(packageId, packageDetailId);
	}

	@PutMapping("/detail")
	public R editCostPackageDetail(@RequestBody CostPackageDetail costPackageDetail){
		return costPackageService.editCostPackageDetail(costPackageDetail);
	}

	@PostMapping("/detail")
	public R saveCostPackageDetail(@RequestBody CostPackageDetail costPackageDetail){
		return costPackageService.saveCostPackageDetail(costPackageDetail);
	}

	@GetMapping("/customer/{custId}")
	public R selectByCustId(@PathVariable("custId") Long custId){
		return costPackageService.selectByCustId(custId);
	}

	@PutMapping("/customer/{custId}")
	public R editCustomerPackage(@PathVariable("custId") Long custId,Long packageId){
		return costPackageService.editCustomerPackage(custId, packageId);
	}

	@GetMapping("/all")
	public R getAllPackage(){
		return costPackageService.getAllPackage();
	}
}
