package com.minigod.zero.trade.service.impl;

import com.alibaba.fastjson.JSON;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.support.Kv;
import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctions;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01100100Request;
import com.minigod.zero.trade.hs.req.EF01110003Request;
import com.minigod.zero.trade.hs.req.EF01110173Request;
import com.minigod.zero.trade.hs.resp.EF01100100VO;
import com.minigod.zero.trade.hs.resp.HsExchangeRateMapVO;
import com.minigod.zero.trade.hs.service.EF01100100;
import com.minigod.zero.trade.hs.service.EF01110003;
import com.minigod.zero.trade.hs.service.EF01180173;
import com.minigod.zero.trade.hs.service.GrmCacheMgr;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import com.minigod.zero.trade.service.IFundSystemService;
import com.minigod.zero.trade.vo.req.FundInfoVO;
import com.minigod.zero.trade.vo.req.FundRecordVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author:yanghu.luo
 * @create: 2023-05-22 14:43
 * @Description: 提供给基金接口调用
 */
@Slf4j
@Service
@RequiredArgsConstructor
//@ConditionalOnProperty(prefix = "trade", name = "type", havingValue = MULTI_SERVER_TYPE_HS)
public class FundSystemServiceImpl implements IFundSystemService {
	private final EF01100100 eF01100100;
	private final EF01180173 eF01180173;
	private final EF01110003 eF01110003;
	private final GrmCacheMgr grmCacheMgr;
	@Override
	public R getSystemStatus() {
		EF01100100Request request = new EF01100100Request();
		request.setFunctionId(GrmFunctions.SV_EXT_SYS_LOGIN);
		GrmResponseVO responseVO = eF01100100.requestData(request);
		if (GrmConstants.GRM_RESPONSE_OK.equals(responseVO.getErrorId())) {
			EF01100100VO ef01100100VO = (EF01100100VO) responseVO.resultData().get(0);
			return R.data(Kv.create().set("systemStatus", ef01100100VO.getSysStatus()));
		}
		return R.fail();
	}

	@Override
	public R getFundRecord(FundRecordVO request) {
		EF01110173Request req = new EF01110173Request();
		req.setFunctionId(GrmFunctions.BROKER_FUNDJOUR_HIS);
		req.setFundAccount(request.getCapitalAccount());
		req.setMoneyType(request.getMoneyType());
		req.setBeginDate(request.getBeginDate());
		req.setEndDate(request.getEndDate());
		GrmResponseVO response = eF01180173.requestData(req);
		if (!response.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
			log.error("getFundRecord EF01180173, error=" + JSON.toJSONString(response));
			return R.fail(response.getErrorMsg());
		}
		return R.data(response.getResult().get("data"));
	}

	@Override
	public R getFundDetail(FundInfoVO request) {
		EF01110003Request req = new EF01110003Request();
		req.setFunctionId(GrmFunctions.BROKER_GET_CACHE_INFO);
		req.setFundAccount(request.getCapitalAccount());
		req.setMoneyType(request.getMoneyType());
		req.setQueryPositions(false);
		GrmResponseVO response = eF01110003.requestData(req);
		if (!response.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
			log.error("getFundDetail EF01110003, error=" + JSON.toJSONString(response));
			return R.fail(response.getErrorMsg());
		}
		return R.data(response.getResult().get("data"));
	}

	@Override
	public R getMoneyRate() {
		// 获取币种汇率
		Map<String, Object> rateMap = new HashMap<>();
		HsExchangeRateMapVO HsExchangeRateMapVO = grmCacheMgr.getHsExchangeRateMapVO(HsConstants.HsMoneyType.CNY.getMoneyType());
		rateMap.put(HsConstants.HsMoneyType.CNY.getMoneyType(), HsExchangeRateMapVO.getExchangeRateMap().values());
		HsExchangeRateMapVO = grmCacheMgr.getHsExchangeRateMapVO(HsConstants.HsMoneyType.USD.getMoneyType());
		rateMap.put(HsConstants.HsMoneyType.USD.getMoneyType(), HsExchangeRateMapVO.getExchangeRateMap().values());
		HsExchangeRateMapVO = grmCacheMgr.getHsExchangeRateMapVO(HsConstants.HsMoneyType.HKD.getMoneyType());
		rateMap.put(HsConstants.HsMoneyType.HKD.getMoneyType(), HsExchangeRateMapVO.getExchangeRateMap().values());
		return R.data(rateMap);
	}
}
