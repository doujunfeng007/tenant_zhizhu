package com.minigod.zero.trade.tiger.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import com.alibaba.fastjson.JSON;
import com.minigod.zero.biz.common.utils.JSONUtil;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.trade.tiger.enums.TigerFunctionsEnum;
import com.minigod.zero.trade.tiger.req.*;
import com.minigod.zero.trade.tiger.resp.AssetsResp;
import com.minigod.zero.trade.tiger.resp.OrderQueryResp;
import com.minigod.zero.trade.tiger.resp.PositionResp;
import com.minigod.zero.trade.tiger.resp.TigerResponse;
import com.minigod.zero.trade.tiger.service.TigerCommonService;
import com.minigod.zero.trade.tiger.util.TigerSignUtil;
import com.minigod.zero.trade.utils.NameBasedIdGenerator;
import com.minigod.zero.trade.utils.RSAUtils;
import com.minigod.zero.trade.vo.sjmb.req.FundUnFreezeReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.minigod.zero.trade.tiger.enums.TigerFunctionsEnum.*;

/**
 * @author chen
 * @date 2025/5/19 15:32
 * @description
 */
@Service
@Slf4j
public class TigerCommonServiceImpl implements TigerCommonService {

	@Resource
	private ZeroRedis zeroRedis;


	@Value("${tiger.appKey}")
	private String appKey;

	@Value("${tiger.appSecret}")
	private String appSecret;

	@Value("${tiger.url}")
	private String tigerUrl;

	@Value("${tiger.grantType}")
	private String grantType;

	@Value("${tiger.rsa.publicKey}")
	private String rsaPublicKey;

	@Value("${tiger.rsa.privateKey}")
	private String rsaPrivateKey;

	@Value("{tiger.masterAccountId}")
	private String masterAccountId;

	@Value("${tiger.broker.clientId}")
	private String brokerClientId;

	@Value("${tiger.sign.publicKey}")
	private String signPublicKey;

	@Value("${tiger.sign.appSecret}")
	private String signAppSecret;

	private final String TIGER_BROKER_TOKEN_KEY = "TIGER_BROKER_TOKEN";


	@Override
	public R getToken() {
		LoginReq loginReq = new LoginReq();
		loginReq.setAppKey(appKey);
		String secret = null;
		try {
			secret = RSAUtils.encrypt(appSecret, rsaPublicKey);
		} catch (Exception e) {
			return R.fail("RSA加密失败");
		}
		loginReq.setAppSecret(secret);
		loginReq.setGrantType(grantType);
		Map<String, Object> formMap = BeanUtil.beanToMap(loginReq);
		TigerResponse respVO = sendRequest(GET_TOKEN, formMap, null);
		if (respVO.isSuccess()) {
			setToken(respVO.getData().toString());
		} else {
			R.fail(respVO.getErrorMessage());
		}
		return null;
	}

	@Override
	public R createUser(CreateUserReq req) {
		return null;
	}

	@Override
	public R createAccount(CreateAccountReq req) {
		return null;
	}

	@Override
	public R deposit(DepositReq req) {
		return null;
	}

	@Override
	public R withdrawal(WithdrawalReq req) {
		return null;
	}

	@Override
	public R queryAssets(UserAccountQuery baseRequest) {
		Map<String, Object> formMap = BeanUtil.beanToMap(baseRequest);
		formMap.put("accountId", baseRequest.getAccountId());
		TigerResponse respVO = sendRequest(TRADING_ASSETS, formMap, null);
		if (respVO.isSuccess()) {
			AssetsResp assetsResp = JSONUtil.fromJson(respVO.getData().toString(), AssetsResp.class);
			return R.data(assetsResp);
		}
		return R.fail(respVO.getErrorMessage());
	}

	@Override
	public R getAccountPositions(PositionsReq positionsReq) {
		Map<String, Object> formMap = BeanUtil.beanToMap(positionsReq);
		TigerResponse respVO = sendRequest(ACCOUNT_POSITIONS, formMap, null);
		if (respVO.isSuccess()) {
			PositionResp positionResp = JSONUtil.fromJson(respVO.getData().toString(), PositionResp.class);
			return R.data(positionResp);
		}
		return R.fail(respVO.getErrorMessage());
	}

	@Override
	public R updateOrder(String accountId, UpdateOrderReq updateOrderReq) {
		Map<String, Object> formMap = new HashMap<>();
		formMap.put("accountId", accountId);

		updateOrderReq.setExternalOrderId(UUID.randomUUID().toString().replace("-", ""));
		updateOrderReq.setRequestTimestamp(System.currentTimeMillis());
		TigerResponse respVO = sendRequest(MODIFY_ORDER, formMap, JSON.toJSONString(updateOrderReq));
		if (respVO.isSuccess()) {
			return R.data(respVO.getData());
		}
		return R.fail(respVO.getErrorMessage());
	}

	@Override
	public R placeOrder(String accountId, PlaceOrderReq placeOrderReq) {
		Map<String, Object> formMap = new HashMap<>();
		formMap.put("accountId", accountId);
		placeOrderReq.setRequestTimestamp(System.currentTimeMillis());
		placeOrderReq.setExternalOrderId(NameBasedIdGenerator.generateId());
		if(StringUtils.isNotBlank(placeOrderReq.getStrike())){
			placeOrderReq.setSecType("OPTION");
		}else{
			placeOrderReq.setSecType("STOCK");
		}
		placeOrderReq.setTradingSessionType("RTH");// 正常交易时段
		TigerResponse respVO = sendRequest(PLACE_ORDER, formMap, JSON.toJSONString(placeOrderReq));
		if (respVO.isSuccess()) {
			return R.data(respVO.getData());
		}
		return R.fail(respVO.getErrorMessage());
	}

	@Override
	public R cancelOrder(CancelOrderReq cancelOrderReq) {
		cancelOrderReq.setRequestTimestamp(System.currentTimeMillis());
		cancelOrderReq.setExternalOrderId(UUID.randomUUID().toString().replace("-", ""));
		Map<String, Object> formMap = BeanUtil.beanToMap(cancelOrderReq);
		TigerResponse respVO = sendRequest(CANCEL_ORDER, formMap, null);
		if (respVO.isSuccess()) {
			return R.data(respVO.getData());
		}
		return R.fail(respVO.getErrorMessage());
	}

	@Override
	public R getMaxmumBuySell(EstimateQuantityReq estimateQuantityReq) {
		return null;
	}

	@Override
	public R getOrders(OrderQueryReq order) {
		Map<String, Object> formMap = BeanUtil.beanToMap(order, false, true);
		TigerResponse respVO = sendRequest(QUERY_ORDER, formMap, null);
		if (respVO.isSuccess()) {
			OrderQueryResp orders = JSONUtil.fromJson(respVO.getData().toString(), OrderQueryResp.class);
			return R.data(orders);
		}
		return R.fail(respVO.getErrorMessage());
	}

	@Override
	public R userQuotePermission(String userId, UserQuotePermissionReq userQuotePermission) {
		return null;
	}

	@Override
	public R userQuotePermissionQuery(UserQuotePermissionQueryReq userQuotePermission) {
		return null;
	}

	@Override
	public R freeze(FundFreezeReq fundFreezeReq) {
		return null;
	}

	@Override
	public R unFreeze(FundUnFreezeReq fundUnFreezeReq) {
		return null;
	}

	@Override
	public R queryAccountPnl(UserAccountQuery baseRequest) {
		return null;
	}

	@Override
	public R queryAccountAvailableCash(Long accountId, String moneyType) {
		return null;
	}

	@Override
	public R getStockRecord(TradeTransactionsReq query) {
		return null;
	}


	private TigerResponse sendRequest(TigerFunctionsEnum tigerFunctionsEnum, Map<String, Object> formMap, String bodyParams) {
		TigerResponse respVO = new TigerResponse();
		String url = tigerUrl + tigerFunctionsEnum.getUrl();
		String result;
		Map<String, String> headerMap = new HashMap<>();
		formMap = Optional.ofNullable(formMap).orElse(new HashMap<>());
		try {
			if (GET_TOKEN == tigerFunctionsEnum) {
				headerMap.put("Content-Type", "application/x-www-form-urlencoded");
				result = HttpUtil.createRequest(tigerFunctionsEnum.getMethod(), url)
					.addHeaders(headerMap)
					.form(formMap)
					.timeout(20000)
					.execute()
					.body();
			} else {
				formMap.put("nonce", UUID.randomUUID().toString().replace("-", ""));
				formMap.put("ts", System.currentTimeMillis());

				String queryParams = formMap.entrySet().stream().map(entry -> entry.getKey() + "=" + entry.getValue()).collect(Collectors.joining("&"));
				url = url + "?" + queryParams;
				String token =getBrokerToken();
				if(StringUtils.isBlank(token)){
					getToken();
				}
				headerMap.put("Authorization", getBrokerToken());
				String sortedData = TigerSignUtil.generateSortData(formMap, appSecret);
				String signature = TigerSignUtil.createSignature(sortedData, rsaPrivateKey);
				headerMap.put("sign", signature);
				result = HttpUtil.createRequest(tigerFunctionsEnum.getMethod(), url)
					.addHeaders(headerMap)
					.body(bodyParams)
					.timeout(20000)
					.execute().body();
			}
			log.info("请求Tiger接口url===" + url);
			log.info("Tiger接口请求参数为=" + bodyParams);
			log.info("Tiger接口返回参数为=" + result);
			respVO = JSONUtil.fromJson(result, TigerResponse.class);
			if ((GET_TOKEN != tigerFunctionsEnum) &&
				"AUTH_INVALID_ACCESS_TOKEN".equals(respVO.getErrorCode())) {
				log.info("未登录做登录操作");
				R loginResp = getToken();
				if (loginResp.isSuccess()) {
					return sendRequest(tigerFunctionsEnum, formMap, bodyParams);
				} else {
					return respVO;
				}
			}
		} catch (Exception e) {
			log.error("请求Tiger接口url===" + url + "返回参数为===" + e.getMessage());
			respVO.setErrorCode("FAIL");
		}
		return respVO;
	}

	private String getBrokerToken() {
		String token = "";
		try {
			token = zeroRedis.get(TIGER_BROKER_TOKEN_KEY);
			if(StringUtils.isEmpty(token)){
				getToken();
				return zeroRedis.get(TIGER_BROKER_TOKEN_KEY);
			}
		} catch (Exception e) {
			log.error("获取redis中的交易token异常", e);
		}
		return token;
	}

	private void setToken(String data) {
		JSONObject mktData = cn.hutool.json.JSONUtil.parseObj(data);
		String accessToken = mktData.get("accessToken").toString();
		String tokenType = mktData.get("tokenType").toString();
		zeroRedis.set(TIGER_BROKER_TOKEN_KEY,tokenType + " " + accessToken);
	}

}
