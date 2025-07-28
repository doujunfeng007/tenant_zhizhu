package com.minigod.zero.data.common.factory;

import com.minigod.zero.data.entity.TExchRate;
import com.minigod.zero.data.mapper.TExchRateMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 汇率转换工厂
 *
 * @Author：jim(liaoguangjie)
 * @Date：2024/5/7 21:47
 * @description：
 */
@Slf4j
@Component
public class ExchangeRateFactory implements InitializingBean {
	/**
	 * 汇率转换结果缓存
	 */
	public static Map<String,BigDecimal> EXCH_RATE_MAP = new HashMap<>();

	@Autowired
	private TExchRateMapper tExchRateMapper;

	@Override
	public void afterPropertiesSet() throws Exception {
		initRate();
	}

	public BigDecimal exchange(BigDecimal assets, String source, String target){
		if (assets == null){
			return BigDecimal.ZERO;
		}
		return assets.multiply(getExchangeRate(source, target));
	}

	private BigDecimal getExchangeRate(String source, String target) {
		// 参数校验
		if (StringUtils.isEmpty(source) || StringUtils.isEmpty(target)) {
			return BigDecimal.ZERO;
		}

		// 获取汇率列表
		if (EXCH_RATE_MAP.isEmpty()) {
			initRate();
		}

		// 查找匹配的汇率
		String key = source +"-"+target;
		BigDecimal value = EXCH_RATE_MAP.get(key);
		return value == null ? BigDecimal.ZERO : value;
	}

	private void initRate(){
		List<TExchRate> list = tExchRateMapper.getAll();
		if (CollectionUtils.isNotEmpty(list)){
			list.stream().forEach(tExchRate -> {
				EXCH_RATE_MAP.put(tExchRate.getSrccurrency()+"-"+tExchRate.getDstcurrency(),tExchRate.getRate());
			});
		}
	}
}
