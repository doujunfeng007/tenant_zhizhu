package com.minigod.zero.bpm.service.impl.api;

import com.alibaba.fastjson.JSONObject;
import com.minigod.zero.bpm.dto.acct.req.CashPolicyReqDto;
import com.minigod.zero.bpm.vo.UserHkidrVo;
import com.minigod.zero.biz.common.utils.HttpClientUtils;
import com.minigod.zero.bpmn.module.stocktransfer.dto.req.StockTransferDto;
import com.minigod.zero.bpm.service.api.IBpmOpenApiService;
import com.minigod.zero.bpm.vo.response.ResponseVO;
import com.minigod.zero.bpmn.module.stocktransfer.dto.req.CashTransferredStockDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BpmOpenApiServiceImpl implements IBpmOpenApiService {

	@Value("${bpm.api.url}")
	private String bpmApiUrl;


	@Override
	public ResponseVO findTransferredStock(StockTransferDto dto) {
		ResponseVO responseVO = null;
		try {
			String url = bpmApiUrl + "/proxy/stockTransfer/findTransferredStock";
			String result = HttpClientUtils.postJson(url, JSONObject.toJSONString(dto), true);
			if(StringUtils.isNotBlank(result)) responseVO = JSONObject.parseObject(result, ResponseVO.class);
		} catch (Exception e) {
			log.error("findTransferredStock error",e);
		}
		return responseVO;
	}

	@Override
	public ResponseVO saveTransferredStock(CashTransferredStockDto dto) {
		ResponseVO responseVO = null;
		try{
			String url = bpmApiUrl + "/proxy/stockTransfer/saveTransferredStock";
			log.info("saveTransferredStock:"+JSONObject.toJSONString(dto));
			String result = HttpClientUtils.postJson(url, JSONObject.toJSONString(dto), true);
			if(StringUtils.isNotBlank(result)) responseVO = JSONObject.parseObject(result, ResponseVO.class);
		} catch (Exception e) {
			log.error("saveTransferredStock error",e);
		}
		return responseVO;
	}

	@Override
	public ResponseVO addPolicyPayment(CashPolicyReqDto dto) {
		ResponseVO responseVO = null;
		try{
			String url = bpmApiUrl + "/proxy/yfLife/addPolicyPayment";
			String result = HttpClientUtils.postJson(url, JSONObject.toJSONString(dto), true);
			if(StringUtils.isNotBlank(result)) responseVO = JSONObject.parseObject(result, ResponseVO.class);
		} catch (Exception e) {
			log.error("addPolicyPayment error",e);
		}
		return responseVO;
	}

	@Override
	public ResponseVO findPolicyPaymentList(CashPolicyReqDto dto) {
		ResponseVO responseVO = null;
		try{
			String url = bpmApiUrl + "/proxy/yfLife/findPolicyPaymentList";
			String result = HttpClientUtils.postJson(url, JSONObject.toJSONString(dto), true);
			if(StringUtils.isNotBlank(result)) responseVO = JSONObject.parseObject(result, ResponseVO.class);
		} catch (Exception e) {
			log.error("findPolicyPaymentList error",e);
		}
		return responseVO;
	}

	@Override
	public ResponseVO needGrantHkidr(UserHkidrVo dto) {
		ResponseVO responseVO = null;
		try{
			String url = bpmApiUrl + "/proxy/secUserInfoChange/needGrantHkidr";
			String result = HttpClientUtils.postJson(url, JSONObject.toJSONString(dto), true);
			if(StringUtils.isNotBlank(result)) responseVO = JSONObject.parseObject(result, ResponseVO.class);
		} catch (Exception e) {
			log.error("needGrantHkidr error",e);
		}
		return responseVO;
	}

	@Override
	public ResponseVO grantHkidr(UserHkidrVo dto) {
		ResponseVO responseVO = null;
		try{
			String url = bpmApiUrl + "/proxy/secUserInfoChange/grantHkidr";
			String result = HttpClientUtils.postJson(url, JSONObject.toJSONString(dto), true);
			if(StringUtils.isNotBlank(result)) responseVO = JSONObject.parseObject(result, ResponseVO.class);
		} catch (Exception e) {
			log.error("grantHkidr error",e);
		}
		return responseVO;
	}
}
