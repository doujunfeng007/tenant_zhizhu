package com.minigod.zero.data.statistics.service.impl;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.DateUtil;
import com.minigod.zero.data.common.factory.ExchangeRateFactory;
import com.minigod.zero.data.enums.CurrencyType;
import com.minigod.zero.data.mapper.TOrderMapper;
import com.minigod.zero.data.mapper.TProductMapper;
import com.minigod.zero.data.statistics.service.ProductAssetsStatisticsService;
import com.minigod.zero.data.vo.ProductAssetVO;
import com.minigod.zero.data.vo.ProductCountVO;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/10/29 10:37
 * @description：产品资产统计
 */
@Log4j2
@Service
public class ProductAssetsStatisticsServiceImpl implements ProductAssetsStatisticsService {

	@Autowired
	private TOrderMapper tOrderMapper;

	@Autowired
	private TProductMapper tProductMapper;

	@Autowired
	private ExchangeRateFactory exchangeRateFactory;


	@Override
	public R selectProductAssetTop5() {
		List<ProductAssetVO> productAssetVOList = tOrderMapper.selectProductAsset();
		if (CollectionUtils.isEmpty(productAssetVOList)) {
			return R.data(new ArrayList<>());
		}
		productAssetVOList.stream().forEach(productAssetVO -> {
			setIssuanceDays(productAssetVO);
			setSort(productAssetVO);
			setScale(productAssetVO);
		});
		List<ProductAssetVO> topFive = productAssetVOList.stream()
			.sorted(Comparator.comparing(ProductAssetVO::getSort).reversed())
			.limit(5)
			.collect(Collectors.toList());
		return R.data(topFive);
	}

	@Override
	public R countProduct() {
		ProductCountVO productCount = tProductMapper.countProduct();
		return R.data(productCount);
	}

	private void setScale(ProductAssetVO productAssetVO) {
		productAssetVO.setAccumulatedTransactionAmount(productAssetVO.getAccumulatedTransactionAmount().setScale(2, RoundingMode.HALF_UP));
		productAssetVO.setAccumulateGainLoss(productAssetVO.getAccumulateGainLoss().setScale(2, RoundingMode.HALF_UP));
	}


	private void setIssuanceDays(ProductAssetVO productAssetVO) {
		if (productAssetVO.getIssueDate() == null) {
			productAssetVO.setIssuanceDays(0L);
			return;
		}
		productAssetVO.setIssuanceDays(DateUtil.between(productAssetVO.getIssueDate(), new Date()).toDays());
	}

	private void setSort(ProductAssetVO productAssetVO) {
		if (CurrencyType.HKD.getCode().equals(productAssetVO.getCurrency())) {
			productAssetVO.setSort(productAssetVO.getAccumulatedTransactionAmount());
		}
		if (CurrencyType.USD.getCode().equals(productAssetVO.getCurrency())) {
			productAssetVO.setSort(
				exchangeRateFactory.exchange(productAssetVO.getAccumulatedTransactionAmount(), CurrencyType.USD.getCode(), CurrencyType.HKD.getCode()));
		}
		if (CurrencyType.CNY.getCode().equals(productAssetVO.getCurrency())) {
			productAssetVO.setSort(
				exchangeRateFactory.exchange(productAssetVO.getAccumulatedTransactionAmount(), CurrencyType.CNY.getCode(), CurrencyType.HKD.getCode()));
		}
	}
}
