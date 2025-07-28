package com.minigod.zero.data.statistics.controller;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.data.statistics.service.ProductAssetsStatisticsService;
import com.minigod.zero.data.statistics.service.TProductService;
import com.minigod.zero.data.vo.HldRateChangeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 产品资产统计
 *
 * @Author：jim(liaoguangjie)
 * @Date：2024/10/29 11:04
 * @description：
 */
@RestController
@Api(tags = "产品资产统计", value = "产品资产统计")
@RequestMapping("/statistics/product-asset")
public class ProductAssetsStatisticsController {

	private final ProductAssetsStatisticsService productAssetsStatisticsService;
	private final TProductService tProductService;

	public ProductAssetsStatisticsController(ProductAssetsStatisticsService productAssetsStatisticsService,
											 TProductService tProductService) {
		this.productAssetsStatisticsService = productAssetsStatisticsService;
		this.tProductService = tProductService;
	}

	@ApiOperation("产品成交top 5")
	@GetMapping("/product_asset_top5")
	R selectProductAssetTop5() {
		return productAssetsStatisticsService.selectProductAssetTop5();
	}

	@ApiOperation("产品发布数量")
	@GetMapping("/product_count")
	public R countProduct() {
		return productAssetsStatisticsService.countProduct();
	}

	@ApiOperation("正在IPO阶段的产品数量")
	@GetMapping("/ipo_product_count")
	public R<Long> ipoCountProduct() {
		return tProductService.ipoCountProduct();
	}

	@ApiOperation("产品发行审批上下架审批数量")
	@GetMapping("/product_type_and_status_count")
	public R<Map<String, List<Map<String, Object>>>> countProductByTypeAndStatus() {
		return tProductService.countProductByTypeAndStatus();
	}

	@ApiOperation("活利得利率变更提醒返回信息")
	@GetMapping("/hld_rate_change")
	public R<HldRateChangeVO> hldRateChange() {
		return R.data(tProductService.hldRateChange());
	}

	@ApiOperation("产品到期提醒数量")
	@GetMapping("/count_product_expired")
	public R<Integer> countProductExpired() {
		return R.data(tProductService.countProductExpired());
	}

	@ApiOperation("产品到期提醒产品编码集合")
	@GetMapping("/product_expired_isin")
	public R<List<String>> productExpiredIsin() {
		return R.data(tProductService.productExpiredIsin());
	}

	@ApiOperation("债券易派息提醒数量")
	@GetMapping("/count_bond_dividend")
	public R<Integer> countBondDividend() {
		return R.data(tProductService.countBondDividend());
	}

	@ApiOperation("债券易派息提醒产品编码集合")
	@GetMapping("/bond_dividend_isin")
	public R<List<String>> bondDividendIsin() {
		return R.data(tProductService.bondDividendIsin());
	}
}
