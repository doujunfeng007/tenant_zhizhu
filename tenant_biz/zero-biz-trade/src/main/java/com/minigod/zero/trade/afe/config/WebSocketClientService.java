package com.minigod.zero.trade.afe.config;

import static com.minigod.zero.trade.afe.common.AfeConstants.SYNC_FUTURE_TIMEOUT;
import static com.minigod.zero.trade.common.ServerConstant.MULTI_SERVER_TYPE_AFE;

import java.io.IOException;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import com.minigod.zero.trade.afe.req.KickoutLogonRequest;
import com.minigod.zero.trade.afe.req.LoginRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;

import com.alibaba.fastjson.JSON;
import com.minigod.zero.biz.common.cache.CacheNames;
import com.minigod.zero.biz.common.constant.TradeCommonConstant;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.trade.afe.common.AfeInterfaceTypeEnum;
import com.minigod.zero.trade.afe.common.AfeMessageCode;
import com.minigod.zero.trade.afe.req.CommonRequest;
import com.minigod.zero.trade.afe.resp.CommonResponse;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author chen
 * @ClassName WebSocketClientService.java
 * @Description TODO
 * @createTime 2024年04月19日 15:06:00
 */
@Component
@Slf4j
@ConditionalOnProperty(value = "trade.type", havingValue = MULTI_SERVER_TYPE_AFE)
public class WebSocketClientService {

	@Value("${afe.websocketUrl}")
	private String websocketUrl;

	@Value("${afe.channel}")
	private String channel;

	/**
	 * 连接最大重试次数
	 */
	@Value("${afe.maxRetry:3}")
	private Integer maxRetry;

	@Value("${afe.agent.userId}")
	private String agentTradeAccount;

	@Value("${afe.agent.password}")
	private String agentPassword;

	@Value("${afe.agent.channel}")
	private String agentChannel;

	@Resource
	private ZeroRedis zeroRedis;

	private final WebSocketClient webSocketClient;
	private final WebSocketHandler webSocketHandler;

	@Autowired
	private AfeMessageStorage afeMessageStorage;

	private final ConcurrentHashMap<String, Queue<CompletableFuture<CommonResponse>>> futureQueueMap = new ConcurrentHashMap<>();

	@Autowired
	public WebSocketClientService(WebSocketClient webSocketClient, WebSocketHandler webSocketHandler) {
		this.webSocketClient = webSocketClient;
		this.webSocketHandler = webSocketHandler;
	}

	public boolean connect(String tradeAccount, int retryCount) {
		boolean flag = false;
		log.info("第{}次连接websocket", retryCount);
		WebSocketSession session = null;
		try {
			session = afeMessageStorage.getUserSessions().get(tradeAccount);
			if (null != session) {
				if (session.isOpen()) {
					//session.close();
					log.info("已存在连接====={},交易账号为=={}", session.getId(), tradeAccount);
				}
			}
			session = webSocketClient.doHandshake(webSocketHandler, websocketUrl).get(30, TimeUnit.SECONDS);
			if (session.isOpen()) {
				afeMessageStorage.getUserSessions().put(tradeAccount, session);
				log.info("{}交易账号连接成功", tradeAccount);
				WebSocketSession w = afeMessageStorage.getUserSessions().get(tradeAccount);
				if (null == w || !w.isOpen()) {
					log.info("{}交易账号连接失败", tradeAccount);
				}
				/**
				 * 存储连接信息
				 */
				afeMessageStorage.getTradeAccountInfo().put(session.getId(), tradeAccount);
				log.info("建立连接成功当前会话信息为=={}", JSONUtil.toJsonStr(afeMessageStorage.getTradeAccountInfo()));
				flag = true;

				if (!afeMessageStorage.getUserSessions().isEmpty()) {
					for (String key : afeMessageStorage.getUserSessions().keySet()) {
						log.info("登录后剩下用户交易账号为：{}，会话id为：{}", key, afeMessageStorage.getUserSessions().get(key).getId());
					}
				}
			}
		} catch (Exception e) {
			log.error("连接websocket异常", e);
			if (retryCount < maxRetry) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ex) {
					// 处理中断异常
				}
				// 递归调用连接方法，增加重试次数
				return connect(tradeAccount, retryCount + 1);
			}
		}
		return flag;
	}

	/**
	 * @param msgType      消息类型
	 * @param reqParams    请求参数
	 * @param tradeAccount 交易账号
	 * @return CommonRequest
	 */
	public <T extends CommonRequest> R<CommonResponse> sendSyncMsg(String msgType, T reqParams, String tradeAccount) {

		reqParams.setCommand(msgType);
		reqParams.setLid(tradeAccount);
		reqParams.setChannel(channel);
		reqParams.setLang("CN");
		String reqId = UUID.randomUUID().toString().replace("-", "");
		if (!(AfeInterfaceTypeEnum.LOGIN.getRequestType().equals(msgType) || AfeInterfaceTypeEnum.RESET_PASSWORD.getResponseType().equals(msgType))) {
			// 非登录请求添加sessionId
			String session = getOpenApiToken(tradeAccount);
			if (StringUtils.isBlank(session)) {
				return R.fail(TradeCommonConstant.TRADE_NOT_LOGIN, "需要重新登录");
			}
			reqParams.setSessionKey(session);
		}
		reqParams.setReqId(reqId);
		String messageReq = JSON.toJSONString(reqParams);
		log.info("请求接口类型={}, 请求id={}, 请求参数={}", msgType, reqId, messageReq);
		WebSocketSession webSocketSession = afeMessageStorage.getUserSessions().get(tradeAccount);
		if (null == webSocketSession || !webSocketSession.isOpen()) {
			log.info("websocket连接已断开=={}", tradeAccount);
			return R.fail(TradeCommonConstant.TRADE_NOT_LOGIN, "需要重新登录");
		}
		CompletableFuture<CommonResponse> future = new CompletableFuture<>();
		try {
			future.orTimeout(SYNC_FUTURE_TIMEOUT, TimeUnit.SECONDS);
			afeMessageStorage.getMsgIdFutures().put(reqId, future);
			log.info("开始发送请求");
			webSocketSession.sendMessage(new TextMessage(messageReq));
			CommonResponse result = future.get();
			log.info("发送请求结束");
			if (null != result) {
				log.info("请求接口类型={}, 请求id={}, 返回参数={}", msgType, reqId, JSONUtil.toJsonStr(result));
				if (AfeMessageCode.SUCCESS.getCode().equals(result.getStatus())) {
					return R.data(result);
				} else {
					if (AfeMessageCode.NEED_LOGIN.getCode().equals(result.getErrorCode()) ||
						AfeMessageCode.SESSION_INVALID.getCode().equals(result.getErrorCode())) {
						return R.fail(TradeCommonConstant.TRADE_NOT_LOGIN, "需要重新登录");
					}
					return R.fail(result.getErrorMessage());
				}
			}
		} catch (InterruptedException e) {
			future.completeExceptionally(e);
			log.error("发送消息异常", e);
			return R.fail("系统异常");
		} catch (IOException e) {
			log.error("系统超时异常", e);
			return R.fail("系统异常");
		} catch (ExecutionException e) {
			log.error("系统超时异常", e);
			log.error("请求超时的消息请求id={}", reqId);
			return R.fail("系统超时");
		} finally {
			afeMessageStorage.getMsgIdFutures().remove(reqId);
		}

		return R.fail();
	}

	/**
	 * 代理人登录
	 *
	 * @return
	 */
	private R agentLogin() {
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setPassword(agentPassword);
		loginRequest.setLoginId(agentTradeAccount);
		loginRequest.setChannel(agentChannel);
		/**
		 * 01 先连接
		 */
		boolean connectStatus = connect(agentTradeAccount, 1);
		if (connectStatus) {
			R result = sendAgentSyncMsg(AfeInterfaceTypeEnum.LOGIN.getRequestType(), loginRequest,
				agentTradeAccount);
			log.info("登录返回==={}", JSONUtil.toJsonStr(result));
			if (result.isSuccess()) {
				/**
				 * 存储会话
				 */
				CommonResponse response = (CommonResponse) result.getData();
				zeroRedis.hSet(CacheNames.TRADE_TOKEN, agentTradeAccount, response.getSessionKey());
				/**
				 * 本地存入登录状态缓存
				 */
				afeMessageStorage.getAgentLoginInfo().put(agentTradeAccount, true);
				return result;
			} else {
				return R.fail(result.getMsg());
			}
		} else {
			return R.fail("连接失败");
		}
	}

	private String getOpenApiToken(String tradeAccount) {
		String token = "";
		try {
			token = zeroRedis.hGet(CacheNames.TRADE_TOKEN, tradeAccount);
		} catch (Exception e) {
			log.error("获取redis中的交易token异常", e);
		}
		return token;
	}

	public ConcurrentHashMap<String, Queue<CompletableFuture<CommonResponse>>> getFutureQueueMap() {
		return futureQueueMap;
	}

	/**
	 * 代理人模式发送消息
	 *
	 * @param msgType
	 * @param reqParams
	 * @param tradeAccount
	 * @return
	 */
	public <T extends CommonRequest> R<CommonResponse> sendAgentSyncMsg(String msgType, T reqParams, String tradeAccount) {

		reqParams.setCommand(msgType);
		reqParams.setLid(agentTradeAccount);
		Boolean isAgentLogin = afeMessageStorage.getAgentLoginInfo().get(agentTradeAccount);
		if (null == isAgentLogin && !AfeInterfaceTypeEnum.LOGIN.getRequestType().equals(msgType)) {
			R result = agentLogin();
			if (!result.isSuccess()) {
				return result;
			}
		}
		reqParams.setChannel(agentChannel);
		reqParams.setLang("CN");
		String reqId = UUID.randomUUID().toString().replace("-", "");
		if (!(AfeInterfaceTypeEnum.LOGIN.getRequestType().equals(msgType) || AfeInterfaceTypeEnum.RESET_PASSWORD.getResponseType().equals(msgType))) {
			// 非登录请求添加sessionId
			String session = getOpenApiToken(agentTradeAccount);
			if (StringUtils.isBlank(session)) {
				return R.fail(TradeCommonConstant.TRADE_NOT_LOGIN, "需要重新登录");
			}
			reqParams.setSessionKey(session);
		}
		reqParams.setReqId(reqId);
		String messageReq = JSON.toJSONString(reqParams);
		log.info("请求接口类型={}, 请求id={}, 请求参数={}", msgType, reqId, messageReq);
		WebSocketSession webSocketSession = afeMessageStorage.getUserSessions().get(agentTradeAccount);
		if (null == webSocketSession || !webSocketSession.isOpen()) {
			log.info("websocket连接已断开=={}", agentTradeAccount);
			afeMessageStorage.getAgentLoginInfo().remove(agentTradeAccount);
			log.info("重新登录");
			return sendAgentSyncMsg(msgType, reqParams, tradeAccount);

		}
		CompletableFuture<CommonResponse> future = new CompletableFuture<>();
		try {
			future.orTimeout(SYNC_FUTURE_TIMEOUT, TimeUnit.SECONDS);
			afeMessageStorage.getMsgIdFutures().put(reqId, future);
			log.info("开始发送请求");
			webSocketSession.sendMessage(new TextMessage(messageReq));
			CommonResponse result = future.get();
			log.info("发送请求结束");
			if (null != result) {
				log.info("请求接口类型={}, 请求id={}, 返回参数={}", msgType, reqId, JSONUtil.toJsonStr(result));
				if (AfeMessageCode.SUCCESS.getCode().equals(result.getStatus())) {
					return R.data(result);
				} else {
					if (AfeMessageCode.NEED_LOGIN.getCode().equals(result.getErrorCode())) {
						afeMessageStorage.getAgentLoginInfo().remove(agentTradeAccount);
						return sendAgentSyncMsg(msgType, reqParams, tradeAccount);
					}
					return R.fail(result.getErrorMessage());
				}
			}
		} catch (InterruptedException e) {
			future.completeExceptionally(e);
			log.error("发送消息异常", e);
			return R.fail("系统异常");
		} catch (IOException e) {
			log.error("系统超时异常", e);
			return R.fail("系统异常");
		} catch (ExecutionException e) {
			log.error("系统超时异常", e);
			log.error("请求超时的消息请求id={}", reqId);
			return R.fail("系统超时");
		} finally {
			afeMessageStorage.getMsgIdFutures().remove(reqId);
		}
		return R.fail();
	}
}
