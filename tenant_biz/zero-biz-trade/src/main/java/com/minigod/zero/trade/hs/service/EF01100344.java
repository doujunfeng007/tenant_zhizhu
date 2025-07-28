package com.minigod.zero.trade.hs.service;

import com.minigod.zero.trade.hs.constants.BeanHelper;
import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01100344Request;
import com.minigod.zero.trade.hs.resp.EF01100344VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 344 查询OQ上下限价格
 */
@Component
@GrmFunctionEntity(requestVo = EF01100344Request.class, responseVo = EF01100344VO.class)
public class EF01100344<T extends GrmRequestVO> extends T2sdkBiz<T> {

	@Override
	protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
		if(request instanceof EF01100344Request){
			EF01100344Request req = (EF01100344Request) request;
			Map<String,String> reqMap = new HashMap();
			reqMap.put(HsConstants.Fields.EXCHANGE_TYPE,"K");
			reqMap.put(HsConstants.Fields.ENTRUST_PRICE,req.getEntrustPrice());
			reqMap.put(HsConstants.Fields.SPREAD_CODE,req.getSpreadCode());
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
			Iterator<Map<String, String>> iterator = list.iterator();
			while (iterator.hasNext()) {
				Map<String, String> rowMap = iterator.next();
				EF01100344VO vo = BeanHelper.copyProperties(rowMap, EF01100344VO.class);
				responseVO.addResultData(vo);
			}
		}
		responseVO.setErrorId(grmDataVO.getErrorId());
		responseVO.setErrorMsg(grmDataVO.getErrorMsg());
		return responseVO;
	}

}
