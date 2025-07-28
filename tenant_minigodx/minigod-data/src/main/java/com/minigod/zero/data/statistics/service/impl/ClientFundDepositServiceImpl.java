package com.minigod.zero.data.statistics.service.impl;

import com.minigod.zero.data.common.factory.ExchangeRateFactory;
import com.minigod.zero.data.enums.CurrencyType;
import com.minigod.zero.data.mapper.ClientFundDepositInfoMapper;
import com.minigod.zero.data.statistics.service.ClientFundDepositService;
import com.minigod.zero.data.vo.ClientDepositStatVO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 客户入金统计服务-入金申请信息
 *
 * @author eric
 * @since 2024-10-29 14:04:08
 */
@Service
public class ClientFundDepositServiceImpl implements ClientFundDepositService {

	private final ClientFundDepositInfoMapper depositInfoMapper;
	private final ExchangeRateFactory exchangeRateFactory;

	public ClientFundDepositServiceImpl(ClientFundDepositInfoMapper depositInfoMapper, ExchangeRateFactory exchangeRateFactory) {
		this.depositInfoMapper = depositInfoMapper;
		this.exchangeRateFactory = exchangeRateFactory;
	}

	/**
	 * 客户入金统计(入金申请信息)
	 *
	 * @return
	 */
	@Override
	public ClientDepositStatVO calculateDepositStats() {
		// 获取所有币种统计结果
		ClientDepositStatVO statVO = depositInfoMapper.getDepositStats();

		// 转换为港币并计算总额
		BigDecimal cnyToHkd = exchangeRateFactory.exchange(statVO.getCnyTotal(), CurrencyType.CNY.getCode(), CurrencyType.HKD.getCode());
		BigDecimal usdToHkd = exchangeRateFactory.exchange(statVO.getUsdTotal(), CurrencyType.USD.getCode(), CurrencyType.HKD.getCode());

		// 计算港币总额
		BigDecimal totalInHKD = cnyToHkd.add(usdToHkd).add(statVO.getHkdTotal());
		statVO.setTotalInHKD(totalInHKD);

		return statVO;
	}
}
