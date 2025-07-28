package com.minigod.zero.customer.factory;

import com.alibaba.fastjson.JSONObject;
import com.minigod.common.exceptions.BusinessException;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.platform.feign.IAccountClient;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/5/7 21:47
 * @description：
 */
@Slf4j
@Component
public class ExchangeRateFactory {

	public static final ThreadLocal<JSONObject> EXCHANGE_RATE = new ThreadLocal<>();

	@Autowired
	private IAccountClient iAccountClient;

	public BigDecimal exchange(String target, String source, BigDecimal amount) {
		if (amount == null) {
			return BigDecimal.ZERO;
		}
		if (StringUtil.isEmpty(source)) {
			return amount;
		}
		if (target.equals(source)) {
			return amount;
		}
		JSONObject exchangeRate = EXCHANGE_RATE.get();
		if (exchangeRate == null) {
			R result = iAccountClient.selectExchangeRate();
			if (!result.isSuccess()) {
				log.error("汇率查询失败,原因:{}", result.getMsg());
				throw new BusinessException("汇率查询失败！");
			}
			exchangeRate = (JSONObject) result.getData();
			EXCHANGE_RATE.set(exchangeRate);
		}

		if (exchangeRate == null) {
			throw new BusinessException("返回汇率为空");
		}
		String key = source + "*" + target;
		BigDecimal rate = new BigDecimal(exchangeRate.getString(key));
		BigDecimal resultAmount = amount.multiply(rate);
		log.info("{} = {} ，汇率={}，换算 {}={}", source, amount, rate, target, resultAmount);
		return resultAmount;
	}

	/**
	 * 获取转换汇率
	 *
	 * @param target
	 * @param source
	 * @return
	 */
	public BigDecimal getRateByCurrency(String target, String source) {
		if (target.equals(source)) {
			return BigDecimal.ONE;
		}
		R result = iAccountClient.selectExchangeRate();
		if (!result.isSuccess()) {
			log.error("汇率查询失败,原因:{}", result.getMsg());
			throw new BusinessException("汇率查询失败！");
		}
		JSONObject exchangeRate = (JSONObject) result.getData();
		if (exchangeRate == null) {
			throw new BusinessException("返回汇率为空");
		}
		String key = source + "*" + target;
		BigDecimal rate = new BigDecimal(exchangeRate.getString(key));
		return rate;
	}

	public void cleanCache() {
		EXCHANGE_RATE.remove();
	}

	/**
	 * 汇率转换
	 *
	 * @param amount
	 * @param rate
	 * @return
	 */
	public BigDecimal exchange(BigDecimal amount, BigDecimal rate) {
		if (rate == null || rate.compareTo(BigDecimal.ZERO) == 0)
			return BigDecimal.ZERO;
		return amount.multiply(rate);
	}
}
