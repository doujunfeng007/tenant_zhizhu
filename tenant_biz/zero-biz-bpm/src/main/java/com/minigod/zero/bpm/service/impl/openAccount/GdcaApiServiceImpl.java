package com.minigod.zero.bpm.service.impl.openAccount;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.minigod.zero.bpm.service.openAccount.IGdcaApiService;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class GdcaApiServiceImpl implements IGdcaApiService {

	@Value("${gdca.servie.appId}")
	private String appid;
	@Value("${gdca.servie.secretkey}")
	private String secretKey;
	@Value("${gdca.servie.qs.api}")
	private String apiUrl;

	@Override
	public R unionPayVerify(String userName, String mobileNo, String bankcardNo, String idcard, String transactionId) {

		try{
			if(StringUtils.isBlank(appid) || StringUtils.isBlank(secretKey) || StringUtils.isBlank(apiUrl)) return R.fail(ResultCode.PARAM_VALID_ERROR);

			Map<String, Object> map = new HashMap<>();
			map.put("appId", appid);
			map.put("secretKey", secretKey);
			map.put("name",userName);
			map.put("idCode",idcard);
			map.put("bankNo",bankcardNo);
			map.put("bankMobile",mobileNo);
			map.put("transactionId", transactionId);
			log.info("unionPayVerify req : {}", JSONUtil.toJsonStr(map));
			String res =  HttpUtil.post(apiUrl+"/qs/api/v1/unionPayVerify",map);
			log.info("unionPayVerify resp : {}",res);
			return R.data(res);

		}catch (Exception e) {
			log.error("unionPayVerify error:" + e.getMessage(),e);
			return R.fail();
		}
	}

	@Override
	public R idNumberCheck(String idcard, String userName, String transactionId) {
 		try {

			if(StringUtils.isBlank(appid) || StringUtils.isBlank(secretKey) || StringUtils.isBlank(apiUrl))
				return R.fail(ResultCode.PARAM_VALID_ERROR);

 			Map<String, Object> map = new HashMap<>();
			map.put("appId", appid);
			map.put("secretKey", secretKey);
			map.put("name",userName);
			map.put("idCode",idcard);
			map.put("transactionId", transactionId);
			log.info("idNumberCheck req : "+ JSONUtil.toJsonStr(map));
			String res = HttpUtil.post(apiUrl+"/qs/api/v1/idnumberCheck",map);
			log.info("idNumberCheck resp : {}",res);
			return R.data(res);
		} catch (Exception e) {
			log.error("idNumberCheck error:" + e.getMessage(),e);
			return R.fail();
		}
	}
}
