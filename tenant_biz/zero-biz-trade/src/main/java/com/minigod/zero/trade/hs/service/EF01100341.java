package com.minigod.zero.trade.hs.service;

import com.minigod.zero.trade.hs.constants.BeanHelper;
import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01100341Request;
import com.minigod.zero.trade.hs.resp.EF01100341VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 341 策略下单
 *
 */
@Component
@GrmFunctionEntity(requestVo = EF01100341Request.class, responseVo = EF01100341VO.class)
public class EF01100341<T extends GrmRequestVO> extends T2sdkBiz<T> {
	@Autowired
	private TradeEventNotifyService tradeEventNotifyService;

	@Override
	protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
		if(request instanceof EF01100341Request){
			EF01100341Request req = (EF01100341Request) request;
			Map<String,String> reqMap = new HashMap();
			reqMap.put(HsConstants.Fields.FUND_ACCOUNT,req.getFundAccount());
			reqMap.put(HsConstants.Fields.STOCK_ACCOUNT,req.getFundAccount());
			reqMap.put(HsConstants.Fields.PASSWORD,req.getPassword());
			reqMap.put(HsConstants.Fields.EXCHANGE_TYPE,req.getExchangeType());
			reqMap.put(HsConstants.Fields.STOCK_CODE,req.getStockCode());
			reqMap.put(HsConstants.Fields.ENTRUST_AMOUNT,req.getEntrustAmount());
			reqMap.put(HsConstants.Fields.ENTRUST_PRICE,req.getEntrustPrice());
			reqMap.put(HsConstants.Fields.ENTRUST_BS,req.getEntrustBs());
			reqMap.put(HsConstants.Fields.ENTRUST_TYPE,"0");// 买卖单
			reqMap.put(HsConstants.Fields.ENTRUST_PROP,req.getEntrustProp());
			reqMap.put(HsConstants.Fields.SHORTSELL_TYPE,"N");// 沽空类型（卖的时候必须要这个字段：目前传入‘N’表示不允许沽空）
			reqMap.put(HsConstants.Fields.HEDGE_FLAG, "B");// 对冲标志（默认传入‘B’默认传入即可，表示没有对冲）
			reqMap.put(HsConstants.Fields.CONDITION_TYPE, req.getConditionType());
			reqMap.put(HsConstants.Fields.TOUCH_PRICE, req.getTouchPrice());
			reqMap.put(HsConstants.Fields.STRATEGY_TYPE, req.getStrategyType());
			reqMap.put(HsConstants.Fields.STRATEGY_ENDDATE, req.getStrategyEnddate());
			oParamMap.putAll(reqMap);
		}
		return oParamMap;
	}

	@Override
	protected <R extends GrmRequestVO> GrmResponseVO parseResponse(GrmDataVO grmDataVO, Map<String, String> oParam,
																   R request) {
		GrmResponseVO responseVO = GrmResponseVO.getInstance();
		EF01100341Request req = (EF01100341Request) request;
		if (grmDataVO.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
			List<Map<String, String>> list = grmDataVO.getResult();
			Iterator<Map<String, String>> iterator = list.iterator();
			EF01100341VO vo = new EF01100341VO();
			while (iterator.hasNext()) {
				Map<String, String> rowMap = iterator.next();
				vo = BeanHelper.copyProperties(rowMap, EF01100341VO.class);
				responseVO.addResultData(vo);
			}
			// 买卖条件单通知
			tradeEventNotifyService.dispatchBuySellEvent(request, oParam, grmDataVO);
		}
		responseVO.setErrorId(grmDataVO.getErrorId());
		responseVO.setErrorMsg(grmDataVO.getErrorMsg());
		return responseVO;
	}

}
