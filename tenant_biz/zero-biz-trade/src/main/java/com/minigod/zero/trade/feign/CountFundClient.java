package com.minigod.zero.trade.feign;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.trade.afe.common.AfeConstants;
import com.minigod.zero.trade.fund.vo.request.FundInfoRequest;
import com.minigod.zero.trade.fund.vo.request.HistoryOrdersRequest;
import com.minigod.zero.trade.service.IFundService;
import com.minigod.zero.trade.vo.sjmb.CurrencyEnum;
import com.minigod.zero.trade.vo.sjmb.req.*;
import com.minigod.zero.trade.vo.sjmb.resp.AssetInfoVO;
import com.minigod.zero.trade.vo.sjmb.resp.FundQueryVO;
import com.minigod.zero.trade.vo.sjmb.resp.JournalOrdersVO;
import com.minigod.zero.trade.vo.sjmb.resp.TotalAssetInfoVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author:yanghu.luo
 * @create: 2023-04-26 10:19
 * @Description: 柜台资金相关接口
 */
@Slf4j
@ApiIgnore()
@RestController
@AllArgsConstructor
public class CountFundClient implements ICounterFundClient {

	@Autowired
	private IFundService fundService;

	@Override
	public R<Object> deposit(FundDepositReq fundDepositReq) {
		return fundService.deposit(fundDepositReq);
	}

	@Override
	public R<Object> withdrawal(FundWithdrawalReq fundWithdrawalReq) {
		return R.success();
//		return fundService.withdrawal(fundWithdrawalReq);
	}

	@Override
	public R<Object> freeze(FundFreezeReq fundFreezeReq) {
		return fundService.freeze(fundFreezeReq);
	}

	@Override
	public R<Object> unFreeze(FundUnFreezeReq fundUnFreezeReq) {
		return fundService.unFreeze(fundUnFreezeReq);
	}

	@Override
	public R<Object> unFreezeWithdraw(FundUnFreezeWithdrawReq fundUnFreezeWithdrawReq) {
		/**
		 * 解冻并扣款
		 */
		return fundService.unFreezeWithdraw(fundUnFreezeWithdrawReq);
	}

	@Override
	public R<FundQueryVO> queryFund(String tradeAccount, String capitalAccount) {
		return fundService.queryFundByAccount(tradeAccount, capitalAccount);
	}

	@Override
	public R<TotalAssetInfoVO> customerPositionList(String tradeAccount, String capitalAccount) {
		TotalAssetInfoVO totalAssetInfo = new TotalAssetInfoVO();
		/**
		 * 汇率
		 */
		FundInfoRequest req = new FundInfoRequest();
		req.setTradeAccount(tradeAccount);
		req.setFundAccount(capitalAccount);
		req.setChannel(AfeConstants.AGENT_CHANNEL);
		R<FundQueryVO> result = fundService.getFundInfo(req);
		BigDecimal asset ;
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
			if(null ==totalAssetInfo.getUsStockAssetInfo()){
				totalAssetInfo.setUsStockAssetInfo(new AssetInfoVO());
			}
			if(null ==totalAssetInfo.getCnyStockAssetInfo()){
				totalAssetInfo.setCnyStockAssetInfo(new AssetInfoVO());
			}

			// TODO 汇率取值需要修改
			asset =getAsset( totalAssetInfo, CurrencyEnum.HKD.getCurrency());
		}else{
			return R.fail();
		}
		totalAssetInfo.setAsset(asset);
		totalAssetInfo.setCurrency(CurrencyEnum.HKD.getCurrency());
		return R.data(totalAssetInfo);
	}

	@Override
	public R<List<JournalOrdersVO>> customerHistoryOrders(String tradeAccount, String capitalAccount, String startDate, String endDate) {

		HistoryOrdersRequest request = new HistoryOrdersRequest();
		request.setTradeAccount(tradeAccount);
		request.setFundAccount(capitalAccount);
		request.setStartDate(startDate);
		request.setEndDate(endDate);
		request.setChannel(AfeConstants.AGENT_CHANNEL);
		return fundService.getHistoryOrders(request);

	}

	private BigDecimal getAsset(TotalAssetInfoVO totalAssetInfo, String targetCurrency) {
		BigDecimal asset =new BigDecimal("0");
		BigDecimal hkAsset =totalAssetInfo.getHkStockAssetInfo().getAsset();
		BigDecimal usAsset =totalAssetInfo.getUsStockAssetInfo().getAsset();
		BigDecimal cnyAsset =totalAssetInfo.getCnyStockAssetInfo().getAsset();

		if("HKD".equals(targetCurrency)){
			usAsset = usAsset.multiply(new BigDecimal("7.76"));
			cnyAsset = cnyAsset.multiply(new BigDecimal("1.09"));

		} else if ("USD".equals(targetCurrency)) {
			hkAsset = hkAsset.multiply(new BigDecimal("0.12"));
			cnyAsset = cnyAsset.multiply(new BigDecimal("0.14"));
		} else if ("CNY".equals(targetCurrency)) {
			hkAsset = hkAsset.multiply(new BigDecimal("0.91"));
			usAsset = usAsset.multiply(new BigDecimal("7.12"));
		}
		asset =hkAsset.add(usAsset).add(cnyAsset);
		return asset;

	}
}
