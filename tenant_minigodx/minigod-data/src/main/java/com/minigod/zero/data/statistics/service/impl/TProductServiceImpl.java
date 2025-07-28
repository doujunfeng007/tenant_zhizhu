package com.minigod.zero.data.statistics.service.impl;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.data.mapper.TProductMapper;
import com.minigod.zero.data.statistics.service.TProductService;
import com.minigod.zero.data.vo.HldRateChangeVO;
import com.minigod.zero.system.feign.IDictBizClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 产品服务接口
 *
 * @author eric
 * @since 2024-10-31 15:27:01
 */
@Service
public class TProductServiceImpl implements TProductService {
	private final TProductMapper tProductMapper;

	@Autowired
	private IDictBizClient dictBizClient;

	public TProductServiceImpl(TProductMapper tProductMapper) {
		this.tProductMapper = tProductMapper;
	}

	/**
	 * 正在IPO阶段的产品数量
	 */
	@Override
	public R<Long> ipoCountProduct() {
		return R.data(tProductMapper.ipoCountProduct());
	}

	@Override
	public HldRateChangeVO hldRateChange() {
		String hldRateChangeMonth = dictBizClient.getValue("hld_rate_change_warn", "活利得利率变更提醒日期间隔（月）").getData();
		Integer month = StringUtils.isNotEmpty(hldRateChangeMonth) ? Integer.valueOf(hldRateChangeMonth) : null;
		List<String> isinList = tProductMapper.hldRateChange(month);
		HldRateChangeVO hldRateChangeVO = new HldRateChangeVO();
		hldRateChangeVO.setIsinList(isinList);
		hldRateChangeVO.setHldRateChangeSum(isinList.size());
		return hldRateChangeVO;
	}

	@Override
	public Integer countProductExpired() {
		String productExpiredMonth = dictBizClient.getValue("product_expired_warn", "产品到期提醒时间间隔（月）").getData();
		Integer month = StringUtils.isNotEmpty(productExpiredMonth) ? Integer.valueOf(productExpiredMonth) : null;
		return tProductMapper.countProductExpired(month);
	}

	@Override
	public List<String> productExpiredIsin() {
		String productExpiredMonth = dictBizClient.getValue("product_expired_warn", "产品到期提醒时间间隔（月）").getData();
		Integer month = StringUtils.isNotEmpty(productExpiredMonth) ? Integer.valueOf(productExpiredMonth) : null;
		return tProductMapper.productExpiredIsin(month);
	}

	@Override
	public Integer countBondDividend() {
		String bondDividendMonth = dictBizClient.getValue("bond_dividend_warn", "债券易派息提醒时间间隔").getData();
		Integer month = StringUtils.isNotEmpty(bondDividendMonth) ? Integer.valueOf(bondDividendMonth) : null;
		return tProductMapper.countBondDividend(month);
	}

	@Override
	public List<String> bondDividendIsin() {
		String bondDividendMonth = dictBizClient.getValue("bond_dividend_warn", "债券易派息提醒时间间隔").getData();
		Integer month = StringUtils.isNotEmpty(bondDividendMonth) ? Integer.valueOf(bondDividendMonth) : null;
		return tProductMapper.bondDividendIsin(month);
	}

	/**
	 * 统计不同产品类型的上下架数量
	 *
	 * @return
	 */
	@Override
	public R<Map<String, List<Map<String, Object>>>> countProductByTypeAndStatus() {
		List<Map<String, Object>> rawStats = tProductMapper.countProductByTypeAndStatus();
		Map<String, List<Map<String, Object>>> result = new HashMap<>();

		// 初始化结果map
		result.put("living", new ArrayList<>());
		result.put("bond", new ArrayList<>());

		// 临时存储每个产品类型的状态统计
		Map<String, Map<String, Long>> tempStats = new HashMap<>();
		tempStats.put("living", new HashMap<>());
		tempStats.put("bond", new HashMap<>());

		// 统计数据
		for (Map<String, Object> stat : rawStats) {
			Long type = (Long) stat.get("type");
			Long status = (Long) stat.get("auditStatus");
			Long count = (Long) stat.get("count");

			String productName = type == 64 ? "living" : "bond";
			String statusName = status == 3 ? "已上架" : "已下架";

			tempStats.get(productName).put(statusName, count);
		}

		// 转换为目标格式
		for (Map.Entry<String, Map<String, Long>> entry : tempStats.entrySet()) {
			String productName = entry.getKey();
			Map<String, Long> statusMap = entry.getValue();

			List<Map<String, Object>> statusList = new ArrayList<>();
			for (Map.Entry<String, Long> statusEntry : statusMap.entrySet()) {
				Map<String, Object> statusItem = new HashMap<>();
				statusItem.put("label", statusEntry.getKey());
				statusItem.put("value", statusEntry.getValue());
				statusList.add(statusItem);
			}

			result.put(productName, statusList);
		}

		return R.data(result);
	}
}
