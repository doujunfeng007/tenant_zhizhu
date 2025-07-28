package com.minigod.zero.trade.service.options;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.trade.fund.vo.request.FundInfoRequest;
import com.minigod.zero.trade.fund.vo.request.HistoryOrdersRequest;
import com.minigod.zero.trade.fund.vo.request.JournalOrdersRequest;
import com.minigod.zero.trade.fund.vo.request.TotalAccountRequest;
import com.minigod.zero.trade.vo.sjmb.resp.AssetInfoVO;
import com.minigod.zero.trade.vo.sjmb.resp.FundQueryVO;
import com.minigod.zero.trade.vo.sjmb.resp.JournalOrdersVO;
import com.minigod.zero.trade.vo.sjmb.resp.PositionVO;

/**
 * @author chen
 * @ClassName OptionFundServiceImpl.java
 * @Description 美股期权
 * @createTime 2024年09月27日 14:09:00
 */
@Service
public class OptionTigerFundServiceImpl implements IOptionFundService {
    @Override
    public AssetInfoVO getTotalAccount(TotalAccountRequest request) {
        String optionsAccount = request.getOptionsAccount();
        String currency = request.getCurrency();
        AssetInfoVO assetInfoVO = new AssetInfoVO();
        assetInfoVO.setCurrency(currency);
        if ("HKD".equals(currency)) {
            assetInfoVO.setAsset(new BigDecimal("100000000"));
        } else if ("USD".equals(currency)) {
            assetInfoVO.setAsset(new BigDecimal("12855200"));
        } else {
            assetInfoVO.setAsset(new BigDecimal("90154200"));
        }
        return assetInfoVO;
    }

    @Override
    public R getFundInfo(FundInfoRequest request) {
        FundQueryVO fundQueryVO = new FundQueryVO();
        List<AssetInfoVO> fundStats;

        fundStats = fundList();
        fundQueryVO.setFundStats(fundStats);

        List<PositionVO> usPosition = createUsPositionList();
        fundQueryVO.setUsopPosition(usPosition);

        return R.data(fundQueryVO);
    }

    @Override
    public R getJournalOrders(JournalOrdersRequest request) {
        List<JournalOrdersVO> ordersVOList;
        ordersVOList = listJournalOrders();
        return R.data(ordersVOList);
    }

	@Override
	public R getHistoryOrders(HistoryOrdersRequest request) {
		List<JournalOrdersVO> ordersVOList;
		ordersVOList = listJournalOrders();
		return R.data(ordersVOList);
	}

	private List<JournalOrdersVO> listJournalOrders() {

        List<JournalOrdersVO> journalOrdersVOList = new ArrayList<>();
        JournalOrdersVO vo = new JournalOrdersVO();
        vo.setOrderNo("123456");
        vo.setExchangeType("USOP");
            // 美股
		vo.setStockCode("NVDA240927C114000");
		vo.setAssetId("NVDA240927C114000.US");

        vo.setOrderTime("2024-09-26 16:54:34");
        vo.setLotSize(1);
        vo.setSecSType(1);
        vo.setStockName("NVDA240927C114000");
        vo.setStockNameZhCN("NVDA240927C114000");
        vo.setStockNameZhHK("NVDA240927C114000");
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
        journalOrdersVOList.add(vo);
        return journalOrdersVOList;
    }

    private List<PositionVO> createUsPositionList() {

        List<PositionVO> positionVOList = new ArrayList<>();

        PositionVO positionVO = new PositionVO();
        positionVO.setExchangeType("US");
        positionVO.setStockCode("NVDA240927C114000");
        positionVO.setAssetId("NVDA240927C114000.US");
        positionVO.setLotSize(1);
        positionVO.setSecSType(1);
        positionVO.setStockName("NVDA240927C114000");
        positionVO.setStockNameZhCN("NVDA240927C114000");
        positionVO.setStockNameZhHK("NVDA240927C114000");
        positionVO.setAvBuyPrice(new BigDecimal("9.00"));
        positionVO.setCostPrice(new BigDecimal("9.00"));
        positionVO.setCurrentQty(new BigDecimal("100"));
        positionVO.setEnableQty(new BigDecimal("100")); // 可用持股数量
        // TODO 要根据行情权限展示价格 从行情获取
        positionVO.setLastPrice(new BigDecimal("10.08"));
        // positionVO.setClosePrice(); // 收盘价
        positionVO.setCurrency("USD");
        positionVO.setMarketValue(new BigDecimal("1008")); // 持仓市值
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
            assetInfoVOList.add(assetInfoVO);
        }
        return assetInfoVOList;
    }
}
