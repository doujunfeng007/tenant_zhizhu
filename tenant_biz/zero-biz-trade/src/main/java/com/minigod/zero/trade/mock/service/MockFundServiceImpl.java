package com.minigod.zero.trade.mock.service;

import static com.minigod.zero.trade.common.ServerConstant.MULTI_SERVER_TYPE_MOCK;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.trade.fund.vo.request.*;
import com.minigod.zero.trade.service.IFundService;
import com.minigod.zero.trade.vo.afe.AfeExchangeTypeEnum;
import com.minigod.zero.trade.vo.sjmb.req.*;
import com.minigod.zero.trade.vo.sjmb.resp.AssetInfoVO;
import com.minigod.zero.trade.vo.sjmb.resp.FundQueryVO;
import com.minigod.zero.trade.vo.sjmb.resp.JournalOrdersVO;
import com.minigod.zero.trade.vo.sjmb.resp.PositionVO;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chen
 * @ClassName MockFundServiceImpl.java
 * @Description TODO
 * @createTime 2024年09月27日 16:23:00
 */
@Slf4j
@Service
@ConditionalOnProperty(value = "trade.type", havingValue = MULTI_SERVER_TYPE_MOCK)
public class MockFundServiceImpl implements IFundService {
    @Override
    public R<FundQueryVO> getFundInfo(FundInfoRequest request) {
        FundQueryVO fundQueryVO = new FundQueryVO();
        List<AssetInfoVO> fundStats;

        /**
         * 查询持仓
         */
        /**
         * 资金
         */
        fundStats = fundList();
        fundQueryVO.setFundStats(fundStats);

        List<PositionVO> hkPosition = createHkPositionList();
        List<PositionVO> usPosition = createUsPositionList();
        List<PositionVO> cnPosition = createCnPositionList();

        fundQueryVO.setHkPosition(hkPosition);
        fundQueryVO.setUsPosition(usPosition);
        fundQueryVO.setCnPosition(cnPosition);

        return R.data(fundQueryVO);
    }

    private List<PositionVO> createCnPositionList() {

        List<PositionVO> positionVOList = new ArrayList<>();

        PositionVO positionVO = new PositionVO();
        positionVO.setExchangeType("SH");
        positionVO.setStockCode("300059");
        positionVO.setAssetId("300059.SH");
        positionVO.setLotSize(100);
        positionVO.setSecSType(1);
        positionVO.setStockName("东方财富");
        positionVO.setStockNameZhCN("东方财富");
        positionVO.setStockNameZhHK("东方财富");
        positionVO.setAvBuyPrice(new BigDecimal("16.12"));
        positionVO.setCostPrice(new BigDecimal("16.12"));
        positionVO.setCurrentQty(new BigDecimal("100"));
        positionVO.setEnableQty(new BigDecimal("100")); // 可用持股数量
        // TODO 要根据行情权限展示价格 从行情获取
        positionVO.setLastPrice(new BigDecimal("16.92"));
        // positionVO.setClosePrice(); // 收盘价
        positionVO.setCurrency("CNY");
        positionVO.setMarketValue(new BigDecimal("1692.00")); // 持仓市值
        BigDecimal incomeBalance =
            (positionVO.getLastPrice().subtract(positionVO.getCostPrice())).multiply(positionVO.getCurrentQty());
        positionVO.setIncomeBalance(incomeBalance);
        if (positionVO.getMarketValue().compareTo(BigDecimal.ZERO) > 0) {
            positionVO.setIncomeBalanceRatio(
                incomeBalance.divide(positionVO.getMarketValue(), new MathContext(5, RoundingMode.HALF_UP)));
        }
        positionVOList.add(positionVO);
        return positionVOList;
    }

    private List<PositionVO> createUsPositionList() {

        List<PositionVO> positionVOList = new ArrayList<>();

        PositionVO positionVO = new PositionVO();
        positionVO.setExchangeType("US");
        positionVO.setStockCode("BABA");
        positionVO.setAssetId("BABA.US");
        positionVO.setLotSize(1);
        positionVO.setSecSType(1);
        positionVO.setStockName("阿里巴巴");
        positionVO.setStockNameZhCN("阿里巴巴");
        positionVO.setStockNameZhHK("阿里巴巴");
        positionVO.setAvBuyPrice(new BigDecimal("400.00"));
        positionVO.setCostPrice(new BigDecimal("300.00"));
        positionVO.setCurrentQty(new BigDecimal("100"));
        positionVO.setEnableQty(new BigDecimal("100")); // 可用持股数量
        // TODO 要根据行情权限展示价格 从行情获取
        positionVO.setLastPrice(new BigDecimal("400.00"));
        // positionVO.setClosePrice(); // 收盘价
        positionVO.setCurrency("USD");
        positionVO.setMarketValue(new BigDecimal("40000.00")); // 持仓市值
        BigDecimal incomeBalance =
            (positionVO.getLastPrice().subtract(positionVO.getCostPrice())).multiply(positionVO.getCurrentQty());
        positionVO.setIncomeBalance(incomeBalance);
        if (positionVO.getMarketValue().compareTo(BigDecimal.ZERO) > 0) {
            positionVO.setIncomeBalanceRatio(
                incomeBalance.divide(positionVO.getMarketValue(), new MathContext(5, RoundingMode.HALF_UP)));
        }
        positionVOList.add(positionVO);
        return positionVOList;
    }

    private List<PositionVO> createHkPositionList() {
        List<PositionVO> hkPosition = new ArrayList<>();

        PositionVO positionVO = new PositionVO();
        positionVO.setExchangeType("HK");
        if (AfeExchangeTypeEnum.HK.getExchangeType().equals(positionVO.getExchangeType())) {
            positionVO.setStockCode("700");
            positionVO.setAssetId("00700.hk");
        }
        positionVO.setLotSize(100);
        positionVO.setSecSType(1);
        positionVO.setStockName("腾讯控股");
        positionVO.setStockNameZhCN("腾讯控股");
        positionVO.setStockNameZhHK("腾讯控股");
        positionVO.setAvBuyPrice(new BigDecimal("400.00"));
        positionVO.setCostPrice(new BigDecimal("300.00"));
        positionVO.setCurrentQty(new BigDecimal("100"));
        positionVO.setEnableQty(new BigDecimal("100")); // 可用持股数量
        // TODO 要根据行情权限展示价格 从行情获取
        positionVO.setLastPrice(new BigDecimal("400.00"));
        // positionVO.setClosePrice(); // 收盘价
        positionVO.setCurrency("HKD");
        positionVO.setMarketValue(new BigDecimal("40000.00")); // 持仓市值
        BigDecimal incomeBalance =
            (positionVO.getLastPrice().subtract(positionVO.getCostPrice())).multiply(positionVO.getCurrentQty());
        positionVO.setIncomeBalance(incomeBalance);
        if (positionVO.getMarketValue().compareTo(BigDecimal.ZERO) > 0) {
            positionVO.setIncomeBalanceRatio(
                incomeBalance.divide(positionVO.getMarketValue(), new MathContext(5, RoundingMode.HALF_UP)));
        }
        hkPosition.add(positionVO);
        return hkPosition;
    }

    private List<AssetInfoVO> fundList() {
        List<AssetInfoVO> assetInfoVOList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            AssetInfoVO assetInfoVO = new AssetInfoVO();
            if (i == 0) {
                assetInfoVO.setCurrency("HKD");
            } else if (i == 1) {
                assetInfoVO.setCurrency("USD");
            } else {
                assetInfoVO.setCurrency("CNY");
            }
            /**
             * TODO 相关字段取值待定
             */
            assetInfoVO.setEnableBalance(new BigDecimal("1000"));
            // TODO 冻结金额
            assetInfoVO.setFrozenBalance(new BigDecimal("2000"));
            // TODO 市值
            assetInfoVO.setMarketValue(new BigDecimal("3000")); // 证券市值
            // 根据持仓计算的
            assetInfoVO.setAsset(new BigDecimal("3000"));
            assetInfoVO.setFetchBalance(new BigDecimal("0"));
            assetInfoVO.setCashOnHold(new BigDecimal("0"));
            assetInfoVO.setCurrentBalance(new BigDecimal("1000"));
            // assetInfoVO.setRiskLevel(1); // 风险水平
            assetInfoVO.setTotalBuyMoney(new BigDecimal("0"));
            assetInfoVO.setMaxExposure(new BigDecimal("0.9")); // 信用额度
            assetInfoVO.setTodayIncome(new BigDecimal("100")); // 今日盈亏
            assetInfoVO.setTodayIncomeRatio(new BigDecimal("0.01")); // 今日盈亏比
            assetInfoVO.setIncomeBalance(new BigDecimal("100")); // 持仓盈亏
            assetInfoVO.setIncomeBalanceRatio(new BigDecimal("0.01")); // 持仓盈亏比
			assetInfoVO.setExcessLiquidity(new BigDecimal("100")); // 剩余流动性
            assetInfoVOList.add(assetInfoVO);
        }
        return assetInfoVOList;
    }

    @Override
    public R getTotalAccount(TotalAccountRequest request) {
        return null;
    }

    @Override
    public R getDetailAccount(TotalAccountRequest request) {
        return null;
    }

    @Override
    public R getDetailAccount(Long custId, String fundAccount, String moneyType) {
        return null;
    }

    @Override
    public R getSingleAccount(SingleAccountRequest request) {
        return null;
    }

    @Override
    public R getUserPortfolio(UserPortfolioRequest request) {
        return null;
    }

    @Override
    public R getJournalOrders(JournalOrdersRequest request) {
		List<JournalOrdersVO> ordersVOList;
		ordersVOList = listJournalOrders();
		return R.data(ordersVOList);

	}

	private List<JournalOrdersVO> listJournalOrders() {

		List<JournalOrdersVO> journalOrdersVOList = new ArrayList<>();
		JournalOrdersVO vo = new JournalOrdersVO();
		vo.setOrderNo("123456");
		vo.setExchangeType("HK");
		// 美股
		vo.setStockCode("00700");
		vo.setAssetId("00700.HK");

		vo.setOrderTime("2024-09-23 16:54:34");
		vo.setLotSize(100);
		vo.setSecSType(1);
		vo.setStockName("腾讯控股");
		vo.setStockNameZhCN("腾讯控股");
		vo.setStockNameZhHK("腾讯控股");
		vo.setBsFlag("B");
		vo.setPrice(new BigDecimal("10.25"));
		vo.setQty(new BigDecimal("100"));
		// TODO 成交价格没有
		vo.setBusinessPrice(new BigDecimal("10.25"));
		vo.setBusinessQty(new BigDecimal("100"));
		// TODO 成交时间没有 成交金额没有
		vo.setBusinessTime("2024-09-26 16:54:34");
		vo.setBusinessBalance(new BigDecimal("0"));
		vo.setOrderType("MO");
		vo.setOrderStatus("8");
		vo.setEntrustDate("2024-09-26");
		vo.setEntrustTime("16:54:34");
		vo.setEntrustProp("MO");

		if ("0".equals(vo.getOrderStatus()) || "1".equals(vo.getOrderStatus()) || "2".equals(vo.getOrderStatus())
			|| "3".equals(vo.getOrderStatus()) || "5".equals(vo.getOrderStatus()) || "7".equals(vo.getOrderStatus())) {
			vo.setCancelable("1");
			vo.setModifiable("1");
		} else {
			vo.setCancelable("0");
			vo.setModifiable("0");
		}
		vo.setOrderValidity("DAY");
		journalOrdersVOList.add(vo);
		return journalOrdersVOList;
	}

	@Override
    public R getHistoryOrders(HistoryOrdersRequest request) {
		List<JournalOrdersVO> ordersVOList;
		ordersVOList = listJournalOrders();
		return R.data(ordersVOList);
    }

    @Override
    public R getJournalFundRecord(JournalFundRecordRequest request) {
        return null;
    }

    @Override
    public R getHistoryFundRecord(HistoryFundRecordRequest request) {
        return null;
    }

    @Override
    public R getJournalStockRecord(JournalStkRecordRequest request) {
        return null;
    }

    @Override
    public R getHistoryStockRecord(HistoryStkRecordRequest request) {
        return null;
    }

    @Override
    public R getMaxmumBuy(MaxmumBuyRequest request) {
        return null;
    }

    @Override
    public R getMaxmumSell(MaxmumSellRequest request) {
        return null;
    }

    @Override
    public R getCurrencyMaster(CurrencyMasterRequest request) {
        return null;
    }

    @Override
    public R getJournalProfit(UserPortfolioRequest request) {
        return null;
    }

    @Override
    public R getStrategyOrders(String request) {
        return null;
    }

    @Override
    public R getOrderCharge(String request) {
        return null;
    }

    @Override
    public R getCompanyAction(String request) {
        return null;
    }

    @Override
    public R<String> getExtractableMoney(String fundAccount, String moneyType) {
        return null;
    }

    @Override
    public R getRiskLevel(String request) {
        return null;
    }

    @Override
    public R getOrdersFee(HistoryOrdersVO request) {
        return null;
    }

    @Override
    public R getStockRecordDetails(StockRecordDetailsVO request) {
        return null;
    }

    @Override
    public R getHistoryStockRecordDetails(StockRecordDetailsVO request) {
        return null;
    }

    @Override
    public R getStockMarginRatio() {
        return null;
    }

    @Override
    public R deposit(FundDepositReq fundDepositReq) {
        return null;
    }

    @Override
    public R withdrawal(FundWithdrawalReq fundWithdrawalReq) {
        return null;
    }

    @Override
    public R freeze(FundFreezeReq fundFreezeReq) {
        return null;
    }

    @Override
    public R unFreeze(FundUnFreezeReq fundUnFreezeReq) {
        return null;
    }

    @Override
    public R unFreezeWithdraw(FundUnFreezeWithdrawReq fundUnFreezeWithdrawReq) {
        return null;
    }

    @Override
    public R<FundQueryVO> queryFundByAccount(String tradeAccount, String capitalAccount) {
        return null;
    }

    @Override
    public R stockMarginRatioSetting(StockMarginSettingReq stockMarginSettingReq) {
        return null;
    }

	@Override
	public R getJournalStrategyOrders(JournalOrdersRequest request) {
		return null;
	}

	@Override
	public R getHistoryStrategyOrders(HistoryOrdersRequest request) {
		return null;
	}
}
