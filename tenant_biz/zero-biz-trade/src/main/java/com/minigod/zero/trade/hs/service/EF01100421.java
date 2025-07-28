package com.minigod.zero.trade.hs.service;

import com.minigod.zero.core.tool.beans.StkInfo;
import com.minigod.zero.trade.hs.constants.*;
import com.minigod.zero.trade.hs.req.EF01100421Request;
import com.minigod.zero.trade.hs.resp.EF01100421VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 421 查询历史委托
 *
 * @author sunline
 *
 */
@Component
@GrmFunctionEntity(requestVo = EF01100421Request.class, responseVo = EF01100421VO.class)
public class EF01100421<T extends GrmRequestVO> extends T2sdkBiz<T> {

	@Override
	protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
		if (request instanceof EF01100421Request) {
			EF01100421Request req = (EF01100421Request) request;
			Map<String, String> reqMap = new HashMap<>();
			reqMap.put(HsConstants.Fields.OP_STATION, req.getOpStation());
			reqMap.put(HsConstants.Fields.FUND_ACCOUNT, req.getFundAccount());
			reqMap.put(HsConstants.Fields.PASSWORD, req.getPassword());
			if(StringUtils.isNotEmpty(req.getExchangeType())){
				reqMap.put(HsConstants.Fields.EXCHANGE_TYPE,req.getExchangeType());
			}
			reqMap.put(HsConstants.Fields.START_DATE, req.getStartDate());
			reqMap.put(HsConstants.Fields.END_DATE, req.getEndDate());
			oParamMap.putAll(reqMap);
		}
		return oParamMap;
	}

	@Override
	protected <R extends GrmRequestVO> GrmResponseVO parseResponse(GrmDataVO grmDataVO, Map<String, String> oParam,
																   R request) {
		GrmResponseVO responseVO = GrmResponseVO.getInstance();
		if (grmDataVO.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
            String sessionId = request.getSessionId();
			EF01100421Request req = (EF01100421Request) request;
			List<Map<String, String>> list = grmDataVO.getResult();
			List<EF01100421VO> listVO = new ArrayList<>();
			Iterator<Map<String, String>> iterator = list.iterator();
			int requestNum = 0;
			if(StringUtils.isNotEmpty(req.getRequestNum())) {
				requestNum = Integer.parseInt(req.getRequestNum());
			}
			String reqAssetId = req.getAssetId();
			int index = 0;
			while (iterator.hasNext()) {
				Map<String, String> rowMap = iterator.next();
				String conditionType = rowMap.get(HsConstants.Fields.CONDITION_TYPE);
				if (req.getFilterType().equals(HsConstants.EntrustFilterType.NORMAL.getCode())) {
					//需要的是普通单,只保留“普通单”和“执行完成的条件单”
					//strategy_status策略状态（1-未执行;2-执行中;3-执行完成;4-暂停;5-强制释放）
					String strategyStatus = rowMap.get(HsConstants.Fields.STRATEGY_STATUS);
					if (StringUtils.isNotBlank(conditionType) && !"3".equals(strategyStatus) ) {
						//未执行完成的条件单，过滤掉
						continue;
					}
				}else if (req.getFilterType().equals(HsConstants.EntrustFilterType.STRATEGY.getCode())) {
					//需要的是条件单
					if (StringUtils.isBlank(conditionType)) {
						//普通委托单，过滤掉
						continue;
					}
				}
				EF01100421VO vo = BeanHelper.copyProperties(rowMap, EF01100421VO.class);
				String assetId = DataFormatUtil.stkCodeToAssetId(vo.getStockCode(),vo.getExchangeType());
				vo.setAssetId(assetId);
				StkInfo assetInfo = grmCacheMgr.getAssetInfo(assetId);
				String entrustStatus = req.getEntrustStatus();
				if(!StringUtils.isEmpty(entrustStatus)){ // 根据状态查询
					if(!entrustStatus.equals(vo.getEntrustStatus())){
						continue;
					}
				}
				if(!StringUtils.isEmpty(reqAssetId)){
					if(!assetId.contains(reqAssetId)){
						continue;
					}
				}
                if (null != assetInfo) {
                    vo.setStockName(getStkNameOrTtional(sessionId,assetInfo.getStkName(), assetInfo.getTraditionalName(), getLang()));
                    vo.setStockNamegb(getStkNameOrTtional(sessionId,assetInfo.getStkName(), assetInfo.getTraditionalName(), getLang()));
                    vo.setSecSType(assetInfo.getSecSType());
                    vo.setLotSize(String.valueOf(assetInfo.getLotSize()));
                }
				vo.setEntrustTime(DataFormatUtil.intTimeCompletion(vo.getEntrustTime()));
				listVO.add(vo);

				index +=1;
				if(requestNum > 0 && index >= requestNum) {
					break;
				}
			}
			responseVO.setResult(listVO);
		}
		responseVO.setErrorId(grmDataVO.getErrorId());
		responseVO.setErrorMsg(grmDataVO.getErrorMsg());
		return responseVO;
	}
}
