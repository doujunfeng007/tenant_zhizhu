package com.minigod.zero.data.statistics.service.impl;

import cn.jiguang.common.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.data.common.factory.ExchangeRateFactory;
import com.minigod.zero.data.enums.CurrencyType;
import com.minigod.zero.data.mapper.TUserHoldingMapper;
import com.minigod.zero.data.statistics.service.CustomerAssetsStatisticsService;
import com.minigod.zero.data.vo.*;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import com.minigod.zero.data.vo.CustomerAssetVO;
import com.minigod.zero.data.vo.MajorAccountAssetsVO;
import com.minigod.zero.data.vo.MajorAccountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/10/28 19:56
 * @description：
 */
@Slf4j
@Service
public class CustomerAssetsStatisticsServiceImpl implements CustomerAssetsStatisticsService {

	@Autowired
	private ExchangeRateFactory exchangeRateFactory;

	@Autowired
	private TUserHoldingMapper tUserHoldingMapper;

	@Override
	public R majorAccountTop10() {
		List<CustomerAssetVO> list = tUserHoldingMapper.customerAssets();
		if (CollectionUtils.isEmpty(list)) {
			return R.data(new ArrayList<>());
		}

		BigDecimal totalAssets = getAllAssets();

		List<MajorAccountAssetsVO> majorAccountList = list.stream()
			.map(customerAsset -> {
				MajorAccountAssetsVO majorAccount = new MajorAccountAssetsVO();
				setMajorAccountAssets(majorAccount, customerAsset);
				setProportion(majorAccount, totalAssets);
				return majorAccount;
			})
			.collect(Collectors.toList());

		List<MajorAccountAssetsVO> topTen = majorAccountList.stream()
			.sorted(Comparator.comparing(MajorAccountAssetsVO::getProportion).reversed())
			.limit(10)
			.collect(Collectors.toList());

		MajorAccountVO majorAccount = new MajorAccountVO();
		majorAccount.setMajorAccountAssetsList(topTen);
		setMajorAccountVO(majorAccount, topTen);
		return R.data(majorAccount);
	}

	/**
	 * 获取按币种统计的累计收益
	 *
	 * @return 累计收益统计列表
	 */
	@Override
	public AccumulatedProfitTotalVO getAccumulatedProfitByCurrency() {
		// 1. 查询客户资产
		List<AccumulatedProfitVO> list = tUserHoldingMapper.selectAccumulatedProfitByCurrency();

		// 2. 初始化结果对象
		AccumulatedProfitTotalVO result = new AccumulatedProfitTotalVO();

		// 总收益(HKD)(已实现+未实现)
		BigDecimal hkdTotalGainLoss = BigDecimal.ZERO;
		// 已实现收益(HDK)
		BigDecimal hkdRealizedGainLoss = BigDecimal.ZERO;
		// 累计现金分红(HKD)
		BigDecimal hkdAccumulatedCashDividends = BigDecimal.ZERO;

		// 总收益(USD)(已实现+未实现)
		BigDecimal usdTotalGainLoss = BigDecimal.ZERO;
		// 已实现收益(USD)
		BigDecimal usdRealizedGainLoss = BigDecimal.ZERO;
		// 累计现金分红(USD)
		BigDecimal usdAccumulatedCashDividends = BigDecimal.ZERO;

		// 总收益(CNY)(已实现+未实现)
		BigDecimal cnyTotalGainLoss = BigDecimal.ZERO;
		// 已实现收益(CNY)
		BigDecimal cnyRealizedGainLoss = BigDecimal.ZERO;
		// 累计现金分红(CNY)
		BigDecimal cnyAccumulatedCashDividends = BigDecimal.ZERO;

		// 3. 遍历结果并分类统计
		for (AccumulatedProfitVO amount : list) {
			String currency = amount.getCurrency();
			// 总收益=已实现+未实现
			BigDecimal totalGainLoss = amount.getTotalGainLoss();
			// 已实现收益
			BigDecimal realizedGainLoss = amount.getRealizedGainLoss();
			// 累计现金分红
			BigDecimal accumulatedCashDividends = amount.getAccumulatedCashDividends();
			if (!StringUtils.isEmpty(currency) && CurrencyType.getByCode(currency) != null) {
				switch (CurrencyType.getByCode(currency)) {
					case HKD:
						hkdTotalGainLoss = hkdTotalGainLoss.add(totalGainLoss);
						hkdRealizedGainLoss = hkdRealizedGainLoss.add(realizedGainLoss);
						hkdAccumulatedCashDividends = hkdAccumulatedCashDividends.add(accumulatedCashDividends);
						break;
					case USD:
						usdTotalGainLoss = usdTotalGainLoss.add(totalGainLoss);
						usdRealizedGainLoss = usdRealizedGainLoss.add(realizedGainLoss);
						usdAccumulatedCashDividends = usdAccumulatedCashDividends.add(accumulatedCashDividends);
						break;
					case CNY:
						cnyTotalGainLoss = cnyTotalGainLoss.add(totalGainLoss);
						cnyRealizedGainLoss = cnyRealizedGainLoss.add(realizedGainLoss);
						cnyAccumulatedCashDividends = cnyAccumulatedCashDividends.add(accumulatedCashDividends);
						break;
					default:
						log.warn("不支持的币种: {}", currency);
				}
			} else {
				log.warn("币种存在错误,当前记录无法正确统计,币种:{},总收益:{},已实现收益:{},累计现金分红:{}", currency, totalGainLoss, realizedGainLoss, accumulatedCashDividends);
			}
		}

		// 总收益(HKD)(已实现+未实现)
		result.setHkdTotalGainLoss(hkdTotalGainLoss);
		// 已实现收益(HKD)
		result.setHkdRealizedGainLoss(hkdRealizedGainLoss);
		// 累计现金分红(HKD)
		result.setHkdAccumulatedCashDividends(hkdAccumulatedCashDividends);

		// 总收益(USD)(已实现+未实现)
		result.setUsdTotalGainLoss(usdTotalGainLoss);
		// 已实现收益(USD)
		result.setUsdRealizedGainLoss(usdRealizedGainLoss);
		// 累计现金分红(USD)
		result.setUsdAccumulatedCashDividends(usdAccumulatedCashDividends);

		// 总收益(CNY)(已实现+未实现)
		result.setCnyTotalGainLoss(cnyTotalGainLoss);
		// 已实现收益(CNY)
		result.setCnyRealizedGainLoss(cnyRealizedGainLoss);
		// 累计现金分红(CNY)
		result.setCnyAccumulatedCashDividends(cnyAccumulatedCashDividends);

		// 所有币种转换为港币后的总额-总收益(HKD)(已实现+未实现)
		BigDecimal hkdTotalAmountGainLoss = hkdTotalGainLoss.add(exchangeRateFactory.exchange(usdTotalGainLoss, CurrencyType.USD.getCode(), CurrencyType.HKD.getCode()))
			.add(exchangeRateFactory.exchange(cnyTotalGainLoss, CurrencyType.CNY.getCode(), CurrencyType.HKD.getCode()));

		result.setHkdTotalAmountGainLoss(hkdTotalAmountGainLoss);

		// 所有币种转换为港币后的总额-已实现收益(HKD)
		BigDecimal hkdTotalRealizedGainLoss = hkdRealizedGainLoss.add(exchangeRateFactory.exchange(usdRealizedGainLoss, CurrencyType.USD.getCode(), CurrencyType.HKD.getCode()))
			.add(exchangeRateFactory.exchange(cnyRealizedGainLoss, CurrencyType.CNY.getCode(), CurrencyType.HKD.getCode()));

		result.setHkdTotalRealizedGainLoss(hkdTotalRealizedGainLoss);

		// 所有币种转换为港币后的总额-累计现金分红(HKD)
		BigDecimal hkdTotalAccumulatedCashDividends = hkdAccumulatedCashDividends.add(exchangeRateFactory.exchange(usdAccumulatedCashDividends, CurrencyType.USD.getCode(), CurrencyType.HKD.getCode()))
			.add(exchangeRateFactory.exchange(cnyAccumulatedCashDividends, CurrencyType.CNY.getCode(), CurrencyType.HKD.getCode()));

		result.setHkdTotalAccumulatedCashDividends(hkdTotalAccumulatedCashDividends.add(hkdTotalAmountGainLoss));

		return result;
	}

	private void setMajorAccountVO(MajorAccountVO majorAccount, List<MajorAccountAssetsVO> topFive) {

		BigDecimal hldTotal = topFive.stream()
			.map(MajorAccountAssetsVO::getHldTotalQuantity)
			.filter(Objects::nonNull)
			.reduce(BigDecimal.ZERO, BigDecimal::add);

		BigDecimal bondTotal = topFive.stream()
			.map(MajorAccountAssetsVO::getBondTotalQuantity)
			.filter(Objects::nonNull)
			.reduce(BigDecimal.ZERO, BigDecimal::add);

		BigDecimal total = topFive.stream()
			.map(MajorAccountAssetsVO::getTotalQuantity)
			.filter(Objects::nonNull)
			.reduce(BigDecimal.ZERO, BigDecimal::add);

		BigDecimal proportion = topFive.stream()
			.map(MajorAccountAssetsVO::getProportion)
			.filter(Objects::nonNull)
			.reduce(BigDecimal.ZERO, BigDecimal::add);

		majorAccount.setHldTotalQuantity(hldTotal);
		majorAccount.setBondTotalQuantity(bondTotal);
		majorAccount.setProportion(proportion);
		majorAccount.setTotalQuantity(total);
	}

	private void setProportion(MajorAccountAssetsVO majorAccount, BigDecimal totalAssets) {
		if (totalAssets.compareTo(BigDecimal.ZERO) <= 0) {
			majorAccount.setProportion(BigDecimal.ZERO);
			return;
		}
		majorAccount.setProportion(majorAccount.getTotalQuantity()
			.divide(totalAssets, 5, RoundingMode.HALF_UP)
			.multiply(new BigDecimal(100))
			.setScale(2, RoundingMode.HALF_UP));
	}


	private void setMajorAccountAssets(MajorAccountAssetsVO majorAccount, CustomerAssetVO customerAsset) {
		majorAccount.setCustomerName(customerAsset.getCustomerName());
		setHldTotalQuantity(majorAccount, customerAsset);
		setBondTotalQuantity(majorAccount, customerAsset);
		setTotalAssets(majorAccount, customerAsset);
	}

	private void setTotalAssets(MajorAccountAssetsVO majorAccount, CustomerAssetVO customerAsset) {
		BigDecimal hkd = customerAsset.getHkdAssets();
		BigDecimal usd = customerAsset.getUsdAssets();
		BigDecimal cny = customerAsset.getCnyAssets();

		BigDecimal total = hkd.add(exchangeRateFactory.exchange(usd, CurrencyType.USD.getCode(), CurrencyType.HKD.getCode()))
			.add(exchangeRateFactory.exchange(cny, CurrencyType.CNY.getCode(), CurrencyType.HKD.getCode()));
		majorAccount.setTotalQuantity(total.setScale(2, RoundingMode.HALF_UP));
	}

	private void setHldTotalQuantity(MajorAccountAssetsVO majorAccount, CustomerAssetVO customerAsset) {
		BigDecimal hkd = customerAsset.getHldHkdTotalQuantity();
		BigDecimal usd = customerAsset.getHldUsdTotalQuantity();
		BigDecimal cny = customerAsset.getHldCnyTotalQuantity();

		BigDecimal total = hkd.add(exchangeRateFactory.exchange(usd, CurrencyType.USD.getCode(), CurrencyType.HKD.getCode()))
			.add(exchangeRateFactory.exchange(cny, CurrencyType.CNY.getCode(), CurrencyType.HKD.getCode()));
		majorAccount.setHldTotalQuantity(total.setScale(2, RoundingMode.HALF_UP));
	}

	private void setBondTotalQuantity(MajorAccountAssetsVO majorAccount, CustomerAssetVO customerAsset) {
		BigDecimal hkd = customerAsset.getBondHkdTotalQuantity();
		BigDecimal usd = customerAsset.getBondUsdTotalQuantity();
		BigDecimal cny = customerAsset.getBondCnyTotalQuantity();

		BigDecimal total = hkd.add(exchangeRateFactory.exchange(usd, CurrencyType.USD.getCode(), CurrencyType.HKD.getCode()))
			.add(exchangeRateFactory.exchange(cny, CurrencyType.CNY.getCode(), CurrencyType.HKD.getCode()));
		majorAccount.setBondTotalQuantity(total.setScale(2, RoundingMode.HALF_UP));
	}

	private BigDecimal getAllAssets() {
		CustomerAssetVO customerAsset = tUserHoldingMapper.selectAllAssets();
		log.info("获取总资产：{}", JSONObject.toJSONString(customerAsset));
		if (customerAsset == null) {
			return BigDecimal.ZERO;
		}
		return customerAsset.getHkdTotalQuantity()
			.add(exchangeRateFactory.exchange(customerAsset.getUsdTotalQuantity(), CurrencyType.USD.getCode(), CurrencyType.HKD.getCode()))
			.add(exchangeRateFactory.exchange(customerAsset.getCnyTotalQuantity(), CurrencyType.CNY.getCode(), CurrencyType.HKD.getCode()));
	}
}
