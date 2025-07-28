package com.minigod.zero.trade.hs.service;

import com.minigod.zero.trade.hs.constants.BeanHelper;
import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01100342Request;
import com.minigod.zero.trade.hs.resp.EF01100342VO;
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
 * 342 策略改单
 *
 */
@Component
@GrmFunctionEntity(requestVo = EF01100342Request.class, responseVo = EF01100342VO.class)
public class EF01100342<T extends GrmRequestVO> extends T2sdkBiz<T> {

	@Autowired
	private TradeEventNotifyService tradeEventNotifyService;

	@Override
	protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
		if(request instanceof EF01100342Request){
			EF01100342Request req = (EF01100342Request) request;
			Map<String,String> reqMap = new HashMap();
			reqMap.put(HsConstants.Fields.FUND_ACCOUNT,req.getFundAccount());
			reqMap.put(HsConstants.Fields.STOCK_ACCOUNT,req.getFundAccount());
			reqMap.put(HsConstants.Fields.PASSWORD,req.getPassword());
			reqMap.put(HsConstants.Fields.EXCHANGE_TYPE,req.getExchangeType());
			reqMap.put(HsConstants.Fields.ENTRUST_NO_FIRST,req.getEntrustNoFirst());
			reqMap.put(HsConstants.Fields.ENTRUST_AMOUNT,req.getEntrustAmount());
			reqMap.put(HsConstants.Fields.ENTRUST_PRICE,req.getEntrustPrice());
			reqMap.put(HsConstants.Fields.ENTRUST_TYPE,"B");// 改单
			reqMap.put(HsConstants.Fields.CONDITION_TYPE, req.getConditionType());
			reqMap.put(HsConstants.Fields.TOUCH_PRICE2, req.getTouchPrice2());
			oParamMap.putAll(reqMap);
		}
		return oParamMap;
	}

	@Override
	protected <R extends GrmRequestVO> GrmResponseVO parseResponse(GrmDataVO grmDataVO, Map<String, String> oParam,
																   R request) {
		GrmResponseVO responseVO = GrmResponseVO.getInstance();
		EF01100342Request req = (EF01100342Request) request;
		if (grmDataVO.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
			List<Map<String, String>> list = grmDataVO.getResult();
			Iterator<Map<String, String>> iterator = list.iterator();
			EF01100342VO vo;
			while (iterator.hasNext()) {
				Map<String, String> rowMap = iterator.next();
				vo = BeanHelper.copyProperties(rowMap, EF01100342VO.class);
				responseVO.addResultData(vo);
			}
			// 触发改条件单通知
			tradeEventNotifyService.dispatchModifyCancelEvent(request, oParam, grmDataVO);
		}
		responseVO.setErrorId(grmDataVO.getErrorId());
		responseVO.setErrorMsg(grmDataVO.getErrorMsg());
		return responseVO;
	}

}
