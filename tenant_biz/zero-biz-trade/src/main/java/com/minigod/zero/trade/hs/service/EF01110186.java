package com.minigod.zero.trade.hs.service;

import com.alibaba.fastjson2.JSON;
import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01110186Request;
import com.minigod.zero.trade.hs.resp.EF01110186VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author:yanghu.luo
 * @create: 2023-05-16 15:13
 * @Description: 获取孖展比率
 */
@Component
@GrmFunctionEntity(requestVo = EF01110186Request.class,responseVo = EF01110186VO.class)
public class EF01110186<T extends GrmRequestVO>  extends T2sdkBiz<T> {
	@Override
	protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
		if (request instanceof EF01110186Request) {
			EF01110186Request req = (EF01110186Request) request;
			Map<String, String> reqMap = new HashMap<>();
			if(StringUtils.isNotEmpty(req.getStockCode())){
				reqMap.put(HsConstants.Fields.STOCK_CODE,req.getStockCode());
			}
			oParamMap.putAll(reqMap);
		}
		return oParamMap;
	}

	@Override
	protected <R extends GrmRequestVO> GrmResponseVO parseResponse(GrmDataVO grmDataVO, Map<String, String> oParam, R request) {
		GrmResponseVO responseVO = GrmResponseVO.getInstance();
		if (grmDataVO.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
			List<Map<String, String>> list = grmDataVO.getResult();
			List<EF01110186VO> listVO = new ArrayList<>();
			Iterator<Map<String, String>> iterator = list.iterator();
			while (iterator.hasNext()) {
				Map<String, String> rowMap = iterator.next();
				EF01110186VO vo = JSON.parseObject(JSON.toJSONString(rowMap), EF01110186VO.class);
				listVO.add(vo);
			}
			responseVO.setResult(listVO);
		}
		responseVO.setErrorId(grmDataVO.getErrorId());
		responseVO.setErrorMsg(grmDataVO.getErrorMsg());
		return responseVO;
	}
}
