package com.minigod.zero.customer.vo;

import com.minigod.zero.customer.entity.CostPackage;
import com.minigod.zero.customer.entity.CostPackageDetail;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/9/12 14:07
 * @description：
 */
@Data
public class CostPackageVO extends CostPackage {
	private String statusName;
	private List<CostPackageDetailVO> hkdDetail;
	private List<CostPackageDetailVO> usdDetail;
	private List<CostPackageDetailVO> cnyDetail;
}
