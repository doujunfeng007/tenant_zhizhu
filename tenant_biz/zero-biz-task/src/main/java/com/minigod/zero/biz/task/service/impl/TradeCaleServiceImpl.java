package com.minigod.zero.biz.task.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.biz.common.constant.OpenApiConstant;
import com.minigod.zero.biz.common.utils.RestTemplateUtil;
import com.minigod.zero.biz.task.mapper.TradeCaleMapper;
import com.minigod.zero.biz.task.service.ITradeCaleService;
import com.minigod.zero.core.redis.cache.Pair;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.core.tool.utils.DateUtil;
import com.minigod.zero.biz.common.mkt.cache.StkTrdCale;
import com.minigod.zero.mkt.entity.TradeCaleEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 交易日历 服务实现类
 *
 * @author 掌上智珠
 * @since 2022-11-17
 */
@Slf4j
@Service
public class TradeCaleServiceImpl extends ServiceImpl<TradeCaleMapper, TradeCaleEntity> implements ITradeCaleService {

	@Resource
	private ZeroRedis zeroRedis;
	@Resource
	private RestTemplateUtil restTemplateUtil;


	@Override
	public void loadTrdCaleToRedis() {
		try {
			List<TradeCaleEntity> list = restTemplateUtil.postSends(OpenApiConstant.FIND_TRADE_CALE, TradeCaleEntity.class, new HashMap<>());
			if (CollectionUtils.isEmpty(list)) {
				return;
			}
			List<Pair<String>> pairs = new ArrayList<>();
			for (TradeCaleEntity entity : list) {
				StkTrdCale stkTrdCale = BeanUtil.copyProperties(entity, StkTrdCale.class);
				pairs.add(new Pair<>(DateUtil.format(stkTrdCale.getNormalDate(), DateUtil.PATTERN_DATE).concat("_").concat(stkTrdCale.getRegionCode()), stkTrdCale));
			}
			if (null != pairs && pairs.size() > 0) {
				zeroRedis.protoBatchSet(StkTrdCale.class, pairs);
			}
			log.info("加载交易日历Redis，{}条", pairs.size());
		} catch (Exception e) {
			log.error("加载交易日历到redis异常", e);
		}
	}
}
