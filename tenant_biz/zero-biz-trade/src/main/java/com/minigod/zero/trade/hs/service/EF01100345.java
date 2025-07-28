package com.minigod.zero.trade.hs.service;

import com.minigod.zero.trade.hs.constants.BeanHelper;
import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01100345Request;
import com.minigod.zero.trade.hs.resp.EF01100345VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 345 查询策略单
 *
 */
@Component
@GrmFunctionEntity(requestVo = EF01100345Request.class, responseVo = EF01100345VO.class)
public class EF01100345<T extends GrmRequestVO> extends T2sdkBiz<T> {

	@Override
	protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
		if(request instanceof EF01100345Request){
			EF01100345Request req = (EF01100345Request) request;
			Map<String,String> reqMap = new HashMap();
			reqMap.put(HsConstants.Fields.ACTION_IN, req.getActionIn());
			reqMap.put(HsConstants.Fields.CONDITION_TYPE, req.getConditionType());
			reqMap.put(HsConstants.Fields.EXCHANGE_TYPE, req.getExchangeType());
			reqMap.put(HsConstants.Fields.ENTRUST_NO, req.getEntrustNo());
			reqMap.put(HsConstants.Fields.INIT_DATE, req.getInitDate());
			oParamMap.putAll(reqMap);
		}
		return oParamMap;
	}

	@Override
	protected <R extends GrmRequestVO> GrmResponseVO parseResponse(GrmDataVO grmDataVO, Map<String, String> oParam,
																   R request) {
		GrmResponseVO responseVO = GrmResponseVO.getInstance();

		if (grmDataVO.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
			List<Map<String, String>> list = grmDataVO.getResult();
			List<EF01100345VO> listVO = new ArrayList<>();
			Iterator<Map<String, String>> iterator = list.iterator();
			while (iterator.hasNext()) {
				Map<String, String> rowMap = iterator.next();
				EF01100345VO vo = BeanHelper.copyProperties(rowMap, EF01100345VO.class);
				listVO.add(vo);
			}
			responseVO.setResult(listVO);
		}
		responseVO.setErrorId(grmDataVO.getErrorId());
		responseVO.setErrorMsg(grmDataVO.getErrorMsg());
		return responseVO;
	}

}
