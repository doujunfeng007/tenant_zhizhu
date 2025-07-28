package com.minigod.zero.trade.service.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.minigod.zero.biz.common.cache.IpoTradeGoPlacingResult;
import com.minigod.zero.biz.common.constant.CommonConstant;
import com.minigod.zero.biz.common.constant.OpenApiConstant;
import com.minigod.zero.biz.common.utils.CommonUtil;
import com.minigod.zero.biz.common.utils.RestTemplateUtil;
import com.minigod.zero.biz.common.vo.mkt.request.IpoVO;
import com.minigod.zero.core.jwt.Jwt2Util;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.enums.CustEnums;
import com.minigod.zero.cust.enums.CustStaticType;
import com.minigod.zero.cust.feign.ICustTradeClient;
import com.minigod.zero.cust.utils.RSANewUtil;
import com.minigod.zero.trade.service.IIpoF10Service;
import com.minigod.zero.trade.vo.JybHkIpoPlacingResultVO;
import com.minigod.zero.trade.vo.PlacingResultVo;
import com.minigod.zero.trade.vo.resp.IPOAppliesResponse;
import com.minigod.zero.trade.vo.resp.IPOApplyDetails;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ipo新股F10服务
 */
@Slf4j
@Service
//@ConditionalOnProperty(prefix = "trade", name = "type", havingValue = "hs")
public class IpoF10ServiceHsImpl implements IIpoF10Service {

	private static final String PARAM_OP_STATION = "opStation";
	@Resource
	private ZeroRedis zeroRedis;

	@Resource
	private ICustTradeClient custTradeClient;
	@Resource
	private RestTemplateUtil restTemplateUtil;

	@Override
	public R getStockDetail(String request) {
		return null;
	}

	@Override
	public R stockHolderInfo(String request) {
		return null;
	}

	@Override
	public R placingResult(IpoVO ipoVO) {
		IpoTradeGoPlacingResult ipoTradeGoPlacingResult = zeroRedis.protoGet(ipoVO.getAssetId(), IpoTradeGoPlacingResult.class);
		PlacingResultVo placingResultResp = new PlacingResultVo();
		if(null == ipoTradeGoPlacingResult){
			HashMap<String, Object> params = new HashMap<>();
			params.put("assetId", ipoVO.getAssetId());
			params.put("tradeUnlock", ipoVO.getTradeUnlock());
			if (ipoVO.getTradeUnlock() == 1){
				params.put("password", ipoVO.getPassword());
				params.put("tradeAccount", ipoVO.getTradeAccount());
				params.put("opStation", ipoVO.getOpStation());
			}
			ipoTradeGoPlacingResult = restTemplateUtil.postSend(OpenApiConstant.PLACING_RESULT,IpoTradeGoPlacingResult.class,RestTemplateUtil.getRequest(params));
		}
		if (ipoTradeGoPlacingResult != null && StringUtils.isNotBlank(ipoTradeGoPlacingResult.getResponse())) {
			List<JybHkIpoPlacingResultVO> list = JSONObject.parseArray(ipoTradeGoPlacingResult.getResponse(), JybHkIpoPlacingResultVO.class);
			List<PlacingResultVo.PlacingResultDetail> detail = Lists.newArrayList();
			if (null != list && list.size() > 0) {
				JybHkIpoPlacingResultVO getPlacingResultVo = list.get(0);
				placingResultResp.setStkName(getPlacingResultVo.getName());
				placingResultResp.setApplyNum(getPlacingResultVo.getNum());
				placingResultResp.setLotSize(getPlacingResultVo.getLot());
				placingResultResp.setMktVal(getPlacingResultVo.getSz());
				placingResultResp.setRate(getPlacingResultVo.getRate());
				placingResultResp.setRlink(getPlacingResultVo.getRLink());
				placingResultResp.setSlink(getPlacingResultVo.getSLink());
				placingResultResp.setClawBack(getPlacingResultVo.getClawBack());
				placingResultResp.setSubscribed(getPlacingResultVo.getSubscribed());
				placingResultResp.setCodesRate(getPlacingResultVo.getCodesRate());
				placingResultResp.setHeadHammer(getPlacingResultVo.getHeadHammer());
				for (JybHkIpoPlacingResultVO p : list) {
					PlacingResultVo.PlacingResultDetail placingResultDetail = new PlacingResultVo.PlacingResultDetail();
					placingResultDetail.setApplyQuantity(p.getStockCount());
					placingResultDetail.setApplyNum(p.getPersonCount());
					placingResultDetail.setQuantityAllotted(null == p.getPriseCount() || "0".equals(p.getPriseCount())?"1":p.getPriseCount());
					placingResultDetail.setRateAllotted(p.getPriseRate());
					placingResultDetail.setRemark(p.getPriseRemark());
					placingResultDetail.setType(p.getGroupN());
					detail.add(placingResultDetail);
				}
			}
			placingResultResp.setDetail(detail);
		}
		// 查询该股票的认购信息
		Map<String, Object> applyMap = new HashMap<>();
		if (ipoVO.getTradeUnlock() == 1) {
			String password = CustEnums.CustType.AUTHOR.getCode() == AuthUtil.getCustType()? CommonConstant.AUTHOR_PWD:ipoVO.getPassword();
			if (StringUtils.isEmpty(password)) {
				try {
					password = Jwt2Util.getTradePassword(AuthUtil.getSessionId());
					log.info("Jwt2Util password = " + password);
				} catch (Exception e) {
					log.error("解析密码异常",e);
				}
				if(StringUtils.isEmpty(password)){
					return R.fail(2401, "请先进行交易解锁");
				}
			}
			String clientId = ipoVO.getTradeAccount();
			R rt = custTradeClient.validPwd(password,clientId);
			if (!rt.isSuccess()) {
				return rt;
			}
			if(CustEnums.CustType.AUTHOR.getCode() == AuthUtil.getCustType()){
				password = CommonConstant.AUTHOR_PWD;
			}else{
				password = RSANewUtil.decrypt(password);
			}
			if (StringUtils.isBlank(password)) {
				return R.fail(CustStaticType.CodeType.PWD_ERROR.getCode(), CustStaticType.CodeType.PWD_ERROR.getMessage());
			}
			String fundAccount = ipoVO.getCapitalAccount();
			Map<String, String> param = new HashMap<String, String>();
			param.put("clientId", clientId);
			param.put("fundAccount", fundAccount);
			param.put("password", password);
			param.put("assetId", ipoVO.getAssetId());
			param.put(PARAM_OP_STATION, ipoVO.getOpStation());
			String jsonResult = CommonUtil.httpPost(OpenApiConstant.IPO_APPLY_INFO, CommonUtil.getRequestJson(param));
			if (CommonUtil.checkCode(jsonResult)) {
				String result = CommonUtil.returnResult(jsonResult);
				if(StringUtils.isNotEmpty(result)){
					IPOAppliesResponse appliesResponse = JSONObject.parseObject(JSONUtil.toJsonStr(result), IPOAppliesResponse.class);
					if (CollectionUtils.isNotEmpty(appliesResponse.getApplies())) {
						IPOApplyDetails ipoApplyDetails = appliesResponse.getApplies().get(0);
						applyMap.put("capitalAccount", ipoApplyDetails.getFundAccount());
						applyMap.put("applyType", ipoApplyDetails.getType());
						applyMap.put("applyQuantity", ipoApplyDetails.getQuantityApply());
					}
				}
			}
		}
		Map<String, Object> rtMap = new HashMap<>();
		rtMap.put("placing", placingResultResp);
		rtMap.put("apply", applyMap);
		// 查询用户行为
		Integer checkStep = 0;
		rtMap.put("checkStep", checkStep);
		return R.data(rtMap);
	}
}
