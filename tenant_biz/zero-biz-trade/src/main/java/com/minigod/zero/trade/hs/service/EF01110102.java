package com.minigod.zero.trade.hs.service;

import com.alibaba.fastjson.JSON;
import com.minigod.zero.trade.hs.constants.DataFormatUtil;
import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01110102Request;
import com.minigod.zero.trade.hs.resp.EF01110102VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 10102 查询最大可买
 *
 */
@Slf4j
@Component
@GrmFunctionEntity(requestVo = EF01110102Request.class, responseVo = EF01110102VO.class)
public class EF01110102<T extends GrmRequestVO> extends T2sdkBiz<T> {
	//@Resource
	//private RestTemplateUtil restTemplateUtil;

	@Override
	protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
		if(request instanceof EF01110102Request){
			EF01110102Request req = (EF01110102Request) request;
			Map<String,String> reqMap = new HashMap();
			reqMap.put(HsConstants.Fields.FUND_ACCOUNT, req.getFundAccount());
			reqMap.put(HsConstants.Fields.STOCK_CODE, req.getStockCode());
			reqMap.put(HsConstants.Fields.EXCHANGE_TYPE, req.getExchangeType());
			reqMap.put(HsConstants.Fields.ENTRUST_PRICE, req.getEntrustPrice());
			setSessionType(req);
			reqMap.put(HsConstants.Fields.SESSION_TYPE, req.getSessionType());
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
			List<EF01110102VO> listVO = new ArrayList<>();
			Iterator<Map<String, String>> iterator = list.iterator();
			while (iterator.hasNext()) {
				Map<String, String> rowMap = iterator.next();
				EF01110102VO vo = JSON.parseObject(JSON.toJSONString(rowMap), EF01110102VO.class);
				listVO.add(vo);
			}
			responseVO.setResult(listVO);
		}
		responseVO.setErrorId(grmDataVO.getErrorId());
		responseVO.setErrorMsg(grmDataVO.getErrorMsg());
		return responseVO;
	}

	/**
	 * 设置req的交易阶段标识（0-正常订单交易（默认），1-盘前交易，2-暗盘交易,3-盘后交易）
	 * */
	private void setSessionType(EF01110102Request req){
		try {
			//港股判断是否暗盘时间
			boolean confidentialTradeTime = false;
			if("K".equalsIgnoreCase(req.getExchangeType())){
				String assetId = DataFormatUtil.stkCodeToAssetId(DataFormatUtil.stockCodeFormat(req.getStockCode(), req.getExchangeType()), req.getExchangeType());
				Boolean confidentialMark = false;//restTemplateUtil.postSend(OpenApiConstant.US_CONFIDENTIAL_MARK, Boolean.class, assetId);
			}
			if (confidentialTradeTime) {
				// 交易阶段标识（0-正常订单交易（默认），1-盘前交易，2-暗盘交易,3-盘后交易）
				req.setSessionType("2");
			}
		} catch (Exception e) {
			log.error("setSessionType error", e);
		}
	}
}
