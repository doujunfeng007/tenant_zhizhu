package com.minigod.zero.customer.back.service;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.customer.entity.CostPackage;
import com.minigod.zero.customer.entity.CostPackageDetail;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/9/11 21:07
 * @description：
 */
public interface CostPackageService {
	R costPackage(String number, String packageName,Integer pageIndex,Integer pageSize);

	R saveCostPackage( CostPackage costPackage);

	R editCostPackage( CostPackage costPackage);

	R costPackage(Long id);

	R deleteCostPackageDetail(Long packageId,Long packageDetailId);

	R editCostPackageDetail(CostPackageDetail costPackageDetail);

	R saveCostPackageDetail(CostPackageDetail costPackageDetail);

	R selectByCustId(Long custId);

	R editCustomerPackage(Long custId,Long packageId);

	R getAllPackage();

	R<CostPackage> selectByPackageNumber(String packageNumber);
}
