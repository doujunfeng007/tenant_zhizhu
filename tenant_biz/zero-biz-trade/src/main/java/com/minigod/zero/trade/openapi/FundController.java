package com.minigod.zero.trade.openapi;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.trade.fund.vo.request.*;
import com.minigod.zero.trade.service.IFundService;
import com.minigod.zero.trade.vo.sjmb.resp.AssetInfoVO;
import com.minigod.zero.trade.vo.sjmb.resp.FundQueryVO;
import com.minigod.zero.trade.vo.sjmb.resp.TotalAssetInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.OPEN_API_PREFIX + "/trade/fund_api")
@Api(value = "帐户相关接口", tags = "帐户相关接口")
public class FundController {
	private final IFundService fundService;


	@PostMapping(value = "/get_fund_info")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "获取资产汇总信息和持仓列表", notes = "传入request")
	public R getFundInfo(@Valid @RequestBody FundInfoRequest request) {

		return fundService.getFundInfo(request);
	}

	@PostMapping(value = "/get_total_account")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "查询客户资金汇总", notes = "传入request")
	public R getTotalAccount(@Valid @RequestBody TotalAccountRequest request) {
		TotalAssetInfoVO totalAssetInfo = new TotalAssetInfoVO();
		/**
		 * 汇率
		 */
		String targetCurrency = request.getCurrency();
		FundInfoRequest req = new FundInfoRequest();
		req.setTradeAccount(request.getTradeAccount());
		req.setFundAccount(request.getFundAccount());
		R<FundQueryVO> result = fundService.getFundInfo(req);
		BigDecimal asset = new BigDecimal("0");
		if (result.isSuccess()) {
			List<AssetInfoVO> fundStats = result.getData().getFundStats();
			for (AssetInfoVO fundStat : fundStats) {
				if ("HKD".equals(fundStat.getCurrency())) {
					totalAssetInfo.setHkStockAssetInfo(fundStat);
				} else if ("USD".equals(fundStat.getCurrency())) {
					totalAssetInfo.setUsStockAssetInfo(fundStat);
				} else if ("CNY".equals(fundStat.getCurrency())) {
					totalAssetInfo.setCnyStockAssetInfo(fundStat);
				}
			}
			if (null == totalAssetInfo.getUsStockAssetInfo()) {
				totalAssetInfo.setUsStockAssetInfo(new AssetInfoVO());
			}
			if (null == totalAssetInfo.getCnyStockAssetInfo()) {
				totalAssetInfo.setCnyStockAssetInfo(new AssetInfoVO());
			}
            // TODO 汇率取值需要修改
			asset = getAsset(totalAssetInfo, targetCurrency);
		} else {
			return result;
		}
		totalAssetInfo.setAsset(asset);
		totalAssetInfo.setCurrency(request.getCurrency());
		return R.data(totalAssetInfo);
	}

	private BigDecimal getAsset(TotalAssetInfoVO totalAssetInfo, String targetCurrency) {
		BigDecimal asset = new BigDecimal("0");
		BigDecimal hkAsset = totalAssetInfo.getHkStockAssetInfo().getAsset();
		BigDecimal usAsset = totalAssetInfo.getUsStockAssetInfo().getAsset();
		BigDecimal cnyAsset = totalAssetInfo.getCnyStockAssetInfo().getAsset();

		if ("HKD".equals(targetCurrency)) {
			usAsset = usAsset.multiply(new BigDecimal("7.76"));
			cnyAsset = cnyAsset.multiply(new BigDecimal("1.09"));

		} else if ("USD".equals(targetCurrency)) {
			hkAsset = hkAsset.multiply(new BigDecimal("0.12"));
			cnyAsset = cnyAsset.multiply(new BigDecimal("0.14"));
		} else if ("CNY".equals(targetCurrency)) {
			hkAsset = hkAsset.multiply(new BigDecimal("0.91"));
			usAsset = usAsset.multiply(new BigDecimal("7.12"));
		}
		asset = hkAsset.add(usAsset).add(cnyAsset);
		return asset;

	}

	@PostMapping(value = "/get_detail_account")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "查询客户资金明细", notes = "传入request")
	public R getDetailAccount(@Valid @RequestBody TotalAccountRequest request) {
		return fundService.getDetailAccount(request);
	}

	@PostMapping(value = "/get_single_account")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "按币种查询资产信息", notes = "传入request")
	public R getSingleAccount(@Valid @RequestBody SingleAccountRequest request) {
		return fundService.getSingleAccount(request);
	}

	@PostMapping(value = "/get_currency_master")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "查询汇率", notes = "传入request")
	public R getCcyMaster(@Valid @RequestBody CurrencyMasterRequest request) {
		return fundService.getCurrencyMaster(request);
	}

	@PostMapping(value = "/get_user_portfolio")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "查询客户持仓", notes = "传入request")
	public R getUserPortfolio(@Valid @RequestBody UserPortfolioRequest request) {
		return fundService.getUserPortfolio(request);
	}

	@PostMapping(value = "/get_journal_orders")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "查询当日委托", notes = "传入request")
	public R getJournalOrders(@Valid @RequestBody JournalOrdersRequest request) {
		return fundService.getJournalOrders(request);
	}

	@PostMapping(value = "/get_history_orders")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "查询历史委托", notes = "传入request")
	public R getHistoryOrders(@Valid @RequestBody HistoryOrdersRequest request) {
		return fundService.getHistoryOrders(request);
	}

	@PostMapping(value = "/get_journal_strategy_orders")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "条件单今日委托", notes = "条件单今日委托")
	public R getJournalStrategyOrders(@RequestBody JournalOrdersRequest request) {
		return fundService.getJournalStrategyOrders(request);
	}

	@PostMapping(value = "/get_history_strategy_orders")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "条件单历史委托", notes = "条件单历史委托")
	public R getHistoryStrategyOrders(@RequestBody HistoryOrdersRequest request) {
		return fundService.getHistoryStrategyOrders(request);
	}

	@PostMapping(value = "/get_journal_fund_record")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "查询当日资金流水", notes = "传入request")
	public R getJournalFundRecord(@Valid @RequestBody JournalFundRecordRequest request) {
		return fundService.getJournalFundRecord(request);
	}

	@PostMapping(value = "/get_history_fund_record")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "查询历史资金流水", notes = "传入request")
	public R getHistoryFundRecord(@Valid @RequestBody HistoryFundRecordRequest request) {
		return fundService.getHistoryFundRecord(request);
	}

	@PostMapping(value = "/get_journal_stock_record")
	@ApiOperationSupport(order = 11)
	@ApiOperation(value = "查询当日股票流水", notes = "传入request")
	public R getJournalStockRecord(@Valid @RequestBody JournalStkRecordRequest request) {
		return fundService.getJournalStockRecord(request);
	}

	@PostMapping(value = "/get_history_stock_record")
	@ApiOperationSupport(order = 12)
	@ApiOperation(value = "查询历史股票流水", notes = "传入request")
	public R getHistoryStockrecord(@Valid @RequestBody HistoryStkRecordRequest request) {
		return fundService.getHistoryStockRecord(request);
	}

	@PostMapping(value = "/get_maxmum_buy")
	@ApiOperationSupport(order = 13)
	@ApiOperation(value = "查询最大可买", notes = "传入request")
	public R getMaxmumBuy(@Valid @RequestBody MaxmumBuyRequest request) {
		return fundService.getMaxmumBuy(request);
	}

	@PostMapping(value = "/get_maxmum_sell")
	@ApiOperationSupport(order = 14)
	@ApiOperation(value = "查询最大可卖", notes = "传入request")
	public R getMaxmumSell(@Valid @RequestBody MaxmumSellRequest request) {
		return fundService.getMaxmumSell(request);
	}

	@PostMapping(value = "/get_order_charge")
	@ApiOperationSupport(order = 15)
	@ApiOperation(value = "查询客户默认费用", notes = "传入request")
	public R getOrderCharge(@Valid @RequestBody String request) {
		return fundService.getOrderCharge(request);
	}

	@PostMapping(value = "/get_orders_fee")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "查询订单费用", notes = "传入request")
	public R getOrdersFee(@Valid @RequestBody HistoryOrdersVO request) {
		return fundService.getOrdersFee(request);
	}

	@PostMapping(value = "/unique_id")
	@ApiOperationSupport(order = 16)
	@ApiOperation(value = "获取唯一ID", notes = "传入request")
	public R uniqueId(@Valid @RequestBody String request) {
		// return fundService.uniqueId(request);
		return R.success();
	}

	@PostMapping(value = "/get_company_action")
	@ApiOperationSupport(order = 17)
	@ApiOperation(value = "查询公司行动", notes = "传入request")
	public R getCompanyAction(@Valid @RequestBody String request) {
		return fundService.getCompanyAction(request);
	}

	@PostMapping(value = "/get_risk_level")
	@ApiOperationSupport(order = 18)
	@ApiOperation(value = "查询风险水平", notes = "传入request")
	public R getRiskLevel(@Valid @RequestBody String request) {
		return fundService.getRiskLevel(request);
	}

	@PostMapping(value = "/get_stock_record_details")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "查询股票成交明细", notes = "传入request")
	public R getStockRecordDetails(@Valid @RequestBody StockRecordDetailsVO request) {
		return fundService.getStockRecordDetails(request);
	}

	@PostMapping(value = "/get_history_stock_record_details")
	@ApiOperationSupport(order = 23)
	@ApiOperation(value = "查询历史股票成交明细", notes = "传入request")
	public R getHistoryStockRecordDetails(@Valid @RequestBody StockRecordDetailsVO request) {
		return fundService.getHistoryStockRecordDetails(request);
	}

	@GetMapping(value = "/get_stock_margin_ratio")
	@ApiOperationSupport(order = 24)
	@ApiOperation(value = "查询证券孖展比例", notes = "")
	public R getStockMarginRatio() {
		return fundService.getStockMarginRatio();
	}

}
